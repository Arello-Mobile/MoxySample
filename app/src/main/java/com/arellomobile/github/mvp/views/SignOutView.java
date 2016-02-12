package com.arellomobile.github.mvp.views;

import com.arellomobile.mvp.GenerateViewState;
import com.arellomobile.mvp.MvpView;

/**
 * Date: 18.01.2016
 * Time: 16:04
 *
 * @author Yuri Shmakov
 */
@GenerateViewState
public interface SignOutView extends MvpView
{
	void signOut();
}
