package com.arellomobile.github.mvp.views;

import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.mvp.MvpView;

/**
 * Date: 27.01.2016
 * Time: 20:00
 *
 * @author Yuri Shmakov
 */
public interface HomeView extends MvpView
{
	void showDetails(int position, Repository repository);
}
