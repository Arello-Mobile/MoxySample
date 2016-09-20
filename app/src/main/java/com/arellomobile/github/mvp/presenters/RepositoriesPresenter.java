package com.arellomobile.github.mvp.presenters;

import java.util.List;

import com.arellomobile.github.app.GithubApi;
import com.arellomobile.github.app.GithubApp;
import com.arellomobile.github.mvp.common.RxUtils;
import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.views.RepositoriesView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import rx.Observable;

/**
 * Date: 22.01.2016
 * Time: 14:39
 *
 * @author Yuri Shmakov
 */
@InjectViewState
public class RepositoriesPresenter extends MvpPresenter<RepositoriesView> {
	private boolean mIsInLoading;

	@Override
	protected void onFirstViewAttach() {
		super.onFirstViewAttach();

		loadRepositories(false);
	}

	public void loadNextRepositories(int currentCount) {
		int page = currentCount / GithubApi.PAGE_SIZE + 1;

		loadData(page, true, false);
	}

	public void loadRepositories(boolean isRefreshing) {
		loadData(1, false, isRefreshing);
	}

	private void loadData(int page, boolean isPageLoading, boolean isRefreshing) {
		if (mIsInLoading) {
			return;
		}
		mIsInLoading = true;

		getViewState().hideError();
		getViewState().onStartLoading();

		showProgress(isPageLoading, isRefreshing);

		final Observable<List<Repository>> observable = RxUtils.wrapRetrofitCall(GithubApp.get().getApi().getUserRepos("JakeWharton", page, GithubApi.PAGE_SIZE));

		RxUtils.wrapAsync(observable)
				.subscribe(repositories -> {
					onLoadingFinish(isPageLoading, isRefreshing);
					onLoadingSuccess(isPageLoading, repositories);
				}, error -> {
					onLoadingFinish(isPageLoading, isRefreshing);
					onLoadingFailed(error);
				});
	}

	private void onLoadingFinish(boolean isPageLoading, boolean isRefreshing) {
		mIsInLoading = false;

		getViewState().onFinishLoading();

		hideProgress(isPageLoading, isRefreshing);
	}

	private void onLoadingSuccess(boolean isPageLoading, List<Repository> repositories) {
		//GithubApp.get().getBus().post(new RepositoriesLoadedEvent(repositories));

		boolean maybeMore = repositories.size() >= GithubApi.PAGE_SIZE;
		if (isPageLoading) {
			getViewState().addRepositories(repositories, maybeMore);
		} else {
			getViewState().setRepositories(repositories, maybeMore);
		}
	}

	private void onLoadingFailed(Throwable error) {
		getViewState().showError(error.toString());
	}

	private void showProgress(boolean isPageLoading, boolean isRefreshing) {
		if (isPageLoading) {
			return;
		}

		if (isRefreshing) {
			getViewState().showRefreshing();
		} else {
			getViewState().showListProgress();
		}
	}

	private void hideProgress(boolean isPageLoading, boolean isRefreshing) {
		if (isPageLoading) {
			return;
		}

		if (isRefreshing) {
			getViewState().hideRefreshing();
		} else {
			getViewState().hideListProgress();
		}
	}

	public void closeError() {
		getViewState().hideError();
	}
}