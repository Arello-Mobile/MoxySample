package com.arellomobile.github.mvp.views;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.mvp.GenerateViewState;
import com.arellomobile.mvp.MvpView;

/**
 * Date: 27.01.2016
 * Time: 21:13
 *
 * @author Yuri Shmakov
 */
@GenerateViewState
public interface RepositoryView extends MvpView
{
	void showRepository(Repository repository);

	void updateLike(boolean isInProgress, boolean isLiked);
}
