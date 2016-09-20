package com.arellomobile.github.mvp;

import java.util.List;

import com.arellomobile.github.app.GithubApi;
import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.models.User;

import retrofit2.Call;

/**
 * Date: 20.09.2016
 * Time: 20:14
 *
 * @author Yuri Shmakov
 */

public class GithubService {

	private GithubApi mGithubApi;

	public GithubService(GithubApi githubApi) {
		mGithubApi = githubApi;
	}


	public Call<User> signIn(String token) {
		return mGithubApi.signIn(token);
	}

	public Call<List<Repository>> getUserRepos(String user, int page, Integer pageSize) {
		return mGithubApi.getUserRepos(user, page, pageSize);
	}
}
