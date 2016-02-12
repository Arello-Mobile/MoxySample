package com.arellomobile.github.mvp.presenters;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Base64;

import com.arellomobile.github.R;
import com.arellomobile.github.app.GithubApp;
import com.arellomobile.github.mvp.common.AuthUtils;
import com.arellomobile.github.mvp.common.RxUtils;
import com.arellomobile.github.mvp.models.User;
import com.arellomobile.github.mvp.views.SignInView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import rx.Observable;

/**
 * Date: 15.01.2016
 * Time: 18:33
 *
 * @author Yuri Shmakov
 */
@InjectViewState
public class SignInPresenter extends MvpPresenter<SignInView>
{
	public void signIn(String email, String password)
	{
		final Resources resources = GithubApp.get().getResources();

		String emailError = null;
		String passwordError = null;

		getViewState().showError(null, null);

		if (TextUtils.isEmpty(email))
		{
			emailError = resources.getString(R.string.error_field_required);
		}

		if (TextUtils.isEmpty(password))
		{
			passwordError = resources.getString(R.string.error_invalid_password);
		}

		if (emailError != null || passwordError != null)
		{
			getViewState().showError(emailError, passwordError);

			return;
		}

		getViewState().showProgress();

		String credentials = String.format("%s:%s", email, password);

		final String token = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

		Observable<User> userObservable = RxUtils.wrapRetrofitCall(GithubApp.get().getApi().signIn(token))
				.doOnNext(user -> AuthUtils.setToken(token));

		RxUtils.wrapAsync(userObservable)
				.subscribe(user -> {
					getViewState().hideProgress();
					getViewState().successSignIn();
				}, exception -> {
					getViewState().hideProgress();
					getViewState().showError(exception.getMessage());
				});
	}

	public void onErrorCancel()
	{
		getViewState().hideError();
	}
}
