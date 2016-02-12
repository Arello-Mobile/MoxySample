package com.arellomobile.github.mvp.views;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.mvp.GenerateViewState;

/**
 * Date: 27.01.2016
 * Time: 20:00
 *
 * @author Yuri Shmakov
 */
@GenerateViewState
public interface HomeView extends RetryableView<ErrorWrapper<String>>
{
	void showDetails(int position, Repository repository);
}
