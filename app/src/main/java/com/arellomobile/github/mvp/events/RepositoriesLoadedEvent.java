package com.arellomobile.github.mvp.events;

import java.util.List;

import com.arellomobile.github.mvp.models.Repository;

/**
 * Date: 26.01.2016
 * Time: 18:32
 *
 * @author Yuri Shmakov
 */
public class RepositoriesLoadedEvent
{
	private List<Repository> mRepositories;

	public RepositoriesLoadedEvent(List<Repository> repositories)
	{
		mRepositories = repositories;
	}

	public List<Repository> getRepositories()
	{
		return mRepositories;
	}
}
