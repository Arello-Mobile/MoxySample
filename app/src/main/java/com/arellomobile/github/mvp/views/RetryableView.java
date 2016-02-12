package com.arellomobile.github.mvp.views;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

/**
 * Date: 09.02.2016
 * Time: 13:04
 *
 * @author Yuri Shmakov
 */
public interface RetryableView<Error> extends MvpView
{
	<T extends MvpPresenter<? extends RetryableView<Error>> & Retryable<Error>, Z> void showError(T presenter, Error error);

	void hideError();
}