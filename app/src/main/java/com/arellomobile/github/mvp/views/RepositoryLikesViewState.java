package com.arellomobile.github.mvp.views;

import com.arellomobile.mvp.viewstate.MvpViewState;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.ViewCommands;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

public class RepositoryLikesViewState extends MvpViewState<RepositoryLikesView> implements RepositoryLikesView

{
	private ViewCommands<RepositoryLikesView> mViewCommands = new ViewCommands<>();

	@Override
	public void restoreState(RepositoryLikesView view)
	{
		if (mViewCommands.isEmpty())
		{
			return;
		}

		mViewCommands.reapply(view);
	}

	@Override
	public void updateLikes(java.util.List<Integer> inProgress, java.util.List<Integer> likedIds)
	{
		updateLikes updateLikes = new updateLikes(inProgress, likedIds);
		mViewCommands.beforeApply(LocalViewCommand.updateLikes, updateLikes);

		if (mViews == null || mViews.isEmpty())
		{
			return;
		}

		for(RepositoryLikesView view : mViews)
		{
			view.updateLikes(inProgress, likedIds);
		}

		mViewCommands.afterApply(LocalViewCommand.updateLikes, updateLikes);
	}

	private enum LocalViewCommand implements ViewCommand<RepositoryLikesView>
	{
		updateLikes(com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy.class, "updateLikes")
				{
					@Override
					public void apply(RepositoryLikesView mvpView, Object presenterId)
					{
						final RepositoryLikesViewState.updateLikes updateLikes = (RepositoryLikesViewState.updateLikes) presenterId;
						mvpView.updateLikes(updateLikes.inProgress, updateLikes.likedIds);
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
		}	}

	private class updateLikes
	{
		java.util.List<Integer> inProgress;
		java.util.List<Integer> likedIds;

		updateLikes(java.util.List<Integer> inProgress, java.util.List<Integer> likedIds)
		{
			this.inProgress = inProgress;
			this.likedIds = likedIds;
		}
	}
}
