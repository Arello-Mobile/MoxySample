package com.arellomobile.github.app;

import android.app.Application;

import com.arellomobile.github.di.AppComponent;
import com.arellomobile.github.di.DaggerAppComponent;
import com.arellomobile.github.di.modules.ContextModule;

/**
 * Date: 18.01.2016
 * Time: 11:22
 *
 * @author Yuri Shmakov
 */
public class GithubApp extends Application {
	private static AppComponent sAppComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		sAppComponent = DaggerAppComponent.builder()
				.contextModule(new ContextModule(this))
				.build();

	}

	public static AppComponent getAppComponent() {
		return sAppComponent;
	}
}
