package com.arellomobile.github.mvp.presenters;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.views.HomeView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

/**
 * Date: 27.01.2016
 * Time: 19:59
 *
 * @author Yuri Shmakov
 */
@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView>
{
	public void onRepositorySelection(int position, Repository repository)
	{
		getViewState().showDetails(position, repository);
	}
}
