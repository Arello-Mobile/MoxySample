package com.arellomobile.github.mvp.views;

import java.util.List;

import com.arellomobile.mvp.GenerateViewState;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Date: 26.01.2016
 * Time: 16:33
 *
 * @author Yuri Shmakov
 */
@GenerateViewState
public interface RepositoryLikesView extends MvpView
{
	@StateStrategyType(AddToEndSingleStrategy.class)
	void updateLikes(List<Integer> inProgress, List<Integer> likedIds);
}