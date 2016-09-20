package com.arellomobile.github.di.modules;


import com.arellomobile.github.app.GithubApi;
import com.arellomobile.github.mvp.GithubService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Date: 8/26/2016
 * Time: 11:58
 *
 * @author Artur Artikov
 */

@Module(includes = {ApiModule.class})
public class GithubModule {
	@Provides
	@Singleton
	public GithubService provideGithubService(GithubApi authApi) {
		return new GithubService(authApi);
	}
}