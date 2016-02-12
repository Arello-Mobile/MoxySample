package com.arellomobile.github.mvp.views;

/**
 * Date: 09.02.2016
 * Time: 13:31
 *
 * @author Yuri Shmakov
 */
public interface SuperRetryableView<T> extends RetryableView<ErrorWrapper<T>>
{
}
