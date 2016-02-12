package com.arellomobile.github.mvp.presenters;

import android.util.Log;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.views.ErrorWrapper;
import com.arellomobile.github.mvp.views.Retryable;
import com.arellomobile.github.mvp.views.SignOutView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpView;

/**
 * Date: 27.01.2016
 * Time: 19:59
 *
 * @author Yuri Shmakov
 */
@InjectViewState
public class HomePresenter<T extends MvpView> extends SuperPresenter<SignOutView, T> implements Retryable<ErrorWrapper<String>>
{
	public void onRepositorySelection(int position, Repository repository)
	{
		//getViewState().showDetails(position, repository);
	}

	@Override
	public void onRetry(ErrorWrapper<String> stringErrorWrapper)
	{
		Log.i("fsd", "Sdf");
	}
}
