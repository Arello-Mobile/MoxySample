package com.arellomobile.github.di;

import android.content.Context;

import com.arellomobile.github.di.modules.BusModule;
import com.arellomobile.github.di.modules.ContextModule;
import com.arellomobile.github.di.modules.GithubModule;
import com.arellomobile.github.mvp.GithubService;
import com.arellomobile.github.mvp.presenters.RepositoriesPresenter;
import com.arellomobile.github.mvp.presenters.RepositoryLikesPresenter;
import com.arellomobile.github.mvp.presenters.SignInPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Date: 8/18/2016
 * Time: 14:49
 *
 * @author Artur Artikov
 */
@Singleton
@Component(modules = {ContextModule.class, BusModule.class, GithubModule.class})
public interface AppComponent {
	Context getContext();
	GithubService getAuthService();
	Bus getBus();

	void inject(SignInPresenter presenter);
	void inject(RepositoriesPresenter repositoriesPresenter);
	void inject(RepositoryLikesPresenter repositoryLikesPresenter);
}
