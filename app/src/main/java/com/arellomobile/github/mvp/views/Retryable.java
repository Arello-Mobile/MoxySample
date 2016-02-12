package com.arellomobile.github.mvp.views;

/**
 * Date: 09.02.2016
 * Time: 13:05
 *
 * @author Yuri Shmakov
 */
public interface Retryable<Error>
{
	void onRetry(Error error);
}