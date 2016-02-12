package com.arellomobile.github.mvp.views;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

/**
 * Date: 10.02.2016
 * Time: 20:04
 *
 * @author Yuri Shmakov
 */
public class HomeViewState extends MvpViewState<HomeView> implements HomeView
{
	private ViewCommands<HomeView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(HomeView view)
	{
		if (mViewCommands.isEmpty())
		{
			return;
		}

		mViewCommands.reapply(view);
	}

	@Override
	public void showDetails(int position, com.arellomobile.github.mvp.models.Repository repository)
	{
		ShowDetailsParams params = new ShowDetailsParams(position, repository);
		mViewCommands.beforeApply(LocalViewCommand.showDetails, params);

		if (mViews == null || mViews.isEmpty())
		{
			return;
		}

		for(HomeView view : mViews)
		{
			view.showDetails(position, repository);
		}

		mViewCommands.afterApply(LocalViewCommand.showDetails, params);
	}

	@Override
	public <T extends com.arellomobile.mvp.MvpPresenter<? extends com.arellomobile.github.mvp.views.RetryableView<com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String>>> & com.arellomobile.github.mvp.views.Retryable<com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String>>, Z> void showError(T presenter, com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String> error)
	{
		ShowErrorParams params = new ShowErrorParams(presenter, error);
		mViewCommands.beforeApply(LocalViewCommand.showError, params);

		if (mViews == null || mViews.isEmpty())
		{
			return;
		}

		for(HomeView view : mViews)
		{
			view.showError(presenter, error);
		}

		mViewCommands.afterApply(LocalViewCommand.showError, params);
	}

	@Override
	public void hideError()
	{
		Void params = null;
		mViewCommands.beforeApply(LocalViewCommand.hideError, params);

		if (mViews == null || mViews.isEmpty())
		{
			return;
		}

		for(HomeView view : mViews)
		{
			view.hideError();
		}

		mViewCommands.afterApply(LocalViewCommand.hideError, params);
	}

	private enum LocalViewCommand implements ViewCommand<HomeView>
	{
		showDetails(com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class, "showDetails")
				{
					@Override
					public void apply(HomeView mvpView, Object paramsObject)
					{
						final ShowDetailsParams params = (ShowDetailsParams) paramsObject;
						mvpView.showDetails(params.position, params.repository);
					}
				},
		showError(com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class, "showError")
				{
					@Override
					public void apply(HomeView mvpView, Object paramsObject)
					{
						final ShowErrorParams<?, ?> params = (ShowErrorParams) paramsObject;
						mvpView.showError(params.presenter, params.error);
					}
				},
		hideError(com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy.class, "hideError")
				{
					@Override
					public void apply(HomeView mvpView, Object paramsObject)
					{
						mvpView.hideError();
					}
				};

		private Class<? extends StateStrategy> mStateStrategyType;
		private String mTag;

		LocalViewCommand(Class<? extends StateStrategy> stateStrategyType, String tag)
		{
			mStateStrategyType = stateStrategyType;
			mTag = tag;
		}

		@Override
		public Class<? extends StateStrategy> getStrategyType()
		{
			return mStateStrategyType;
		}

		@Override
		public String getTag()
		{
			return mTag;
		}
	}

	private class ShowDetailsParams
	{
		int position;
		com.arellomobile.github.mvp.models.Repository repository;

		ShowDetailsParams(int position, com.arellomobile.github.mvp.models.Repository repository)
		{
			this.position = position;
			this.repository = repository;
		}
	}

	private class ShowErrorParams<T extends com.arellomobile.mvp.MvpPresenter<? extends com.arellomobile.github.mvp.views.RetryableView<com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String>>> & com.arellomobile.github.mvp.views.Retryable<com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String>>, Z>
	{
		T presenter;
		com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String> error;

		ShowErrorParams(T presenter, com.arellomobile.github.mvp.views.ErrorWrapper<java.lang.String> error)
		{
			this.presenter = presenter;
			this.error = error;
		}
	}
}