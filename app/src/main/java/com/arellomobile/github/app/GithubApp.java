package com.arellomobile.github.app;

import java.lang.reflect.Field;

import com.arellomobile.mvp.MvpApplication;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Date: 18.01.2016
 * Time: 11:22
 *
 * @author Yuri Shmakov
 */
public class GithubApp extends MvpApplication {
	private static GithubApp sInstance;
	private GithubApi mApi;
	private Bus mBus;

	public GithubApp() {
		sInstance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		final Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setFieldNamingStrategy(new CustomFieldNamingPolicy())
				.setPrettyPrinting()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.serializeNulls()
				.create();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com")
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();

		mApi = retrofit.create(GithubApi.class);

		mBus = new Bus();
	}

	public static GithubApp get() {
		return sInstance;
	}

	public GithubApi getApi() {
		return mApi;
	}

	public Bus getBus() {
		return mBus;
	}

	private static class CustomFieldNamingPolicy implements FieldNamingStrategy {
		@Override
		public String translateName(Field field) {
			String name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
			name = name.substring(2, name.length()).toLowerCase();
			return name;
		}
	}
}
