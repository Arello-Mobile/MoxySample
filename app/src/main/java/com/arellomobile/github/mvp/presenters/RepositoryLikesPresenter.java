package com.arellomobile.github.mvp.presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.arellomobile.github.app.GithubApp;
import com.arellomobile.github.mvp.common.RxUtils;
import com.arellomobile.github.mvp.events.RepositoriesLoadedEvent;
import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.views.RepositoryLikesView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.squareup.otto.Subscribe;

import rx.Observable;

/**
 * Date: 26.01.2016
 * Time: 16:32
 *
 * @author Yuri Shmakov
 */
@InjectViewState
public class RepositoryLikesPresenter extends MvpPresenter<RepositoryLikesView>
{
	public static final String TAG = "RepositoryLikesPresenter";

	private List<Integer> mInProgress = new ArrayList<>();
	private List<Integer> mLikedIds = new ArrayList<>();

	public RepositoryLikesPresenter()
	{
		super();

		GithubApp.get().getBus().register(this);
	}

	@Subscribe
	public void repositoriesLoaded(RepositoriesLoadedEvent repositoriesLoadedEvent)
	{
		final Random random = new Random();

		for (Repository repository : repositoriesLoadedEvent.getRepositories())
		{
			if (!mInProgress.contains(repository.getId()))
			{
				continue;
			}

			if (random.nextBoolean())
			{
				mLikedIds.add(repository.getId());
			}
			else
			{
				mLikedIds.remove(Integer.valueOf(repository.getId()));
			}

			mInProgress.remove(Integer.valueOf(repository.getId()));
		}

		getViewState().updateLikes(mInProgress, mLikedIds);
	}

	public void toggleLike(int id)
	{
		if (mInProgress.contains(id))
		{
			return;
		}

		mInProgress.add(id);

		getViewState().updateLikes(mInProgress, mLikedIds);

		final Observable<Boolean> toggleObservable = Observable.create(subscriber -> {
			try
			{
				TimeUnit.SECONDS.sleep(3);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			subscriber.onNext(!mLikedIds.contains(id));
		});

		RxUtils.wrapAsync(toggleObservable)
				.subscribe(isLiked -> {
					onComplete(id, isLiked);
				}, throwable -> {
					onFail(id);
				});
	}

	private void onComplete(int id, Boolean isLiked)
	{
		if (!mInProgress.contains(id))
		{
			return;
		}

		mInProgress.remove(Integer.valueOf(id));
		if (isLiked)
		{
			mLikedIds.add(id);
		}
		else
		{
			mLikedIds.remove(Integer.valueOf(id));
		}

		getViewState().updateLikes(mInProgress, mLikedIds);
	}

	private void onFail(int id)
	{
		if (!mInProgress.contains(id))
		{
			return;
		}

		mInProgress.remove(Integer.valueOf(id));
		getViewState().updateLikes(mInProgress, mLikedIds);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		GithubApp.get().getBus().unregister(this);
	}
}
