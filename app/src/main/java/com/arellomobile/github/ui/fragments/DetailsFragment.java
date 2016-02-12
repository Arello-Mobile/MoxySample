package com.arellomobile.github.ui.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arellomobile.github.R;
import com.arellomobile.github.mvp.common.MvpAppCompatFragment;
import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.presenters.RepositoryLikesPresenter;
import com.arellomobile.github.mvp.presenters.RepositoryPresenter;
import com.arellomobile.github.mvp.views.RepositoryLikesView;
import com.arellomobile.github.mvp.views.RepositoryView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 27.01.2016
 * Time: 20:17
 *
 * @author Yuri Shmakov
 */
public class DetailsFragment extends MvpAppCompatFragment implements RepositoryView, RepositoryLikesView
{
	public static final String ARGS_REPOSITORY = "argsRepository";

	@InjectPresenter
	RepositoryPresenter mRepositoryPresenter;
	@InjectPresenter(type = PresenterType.GLOBAL, tag = RepositoryLikesPresenter.TAG)
	RepositoryLikesPresenter mRepositoryLikesPresenter;

	@Bind(R.id.fragment_repository_details_text_view_title)
	TextView mTitleTextView;
	@Bind(R.id.fragment_repository_details_image_button_like)
	ImageButton mLikeImageButton;
	private Repository mRepository;

	public static DetailsFragment getInstance(Repository repository)
	{
		DetailsFragment fragment = new DetailsFragment();

		Bundle args = new Bundle();
		args.putSerializable(ARGS_REPOSITORY, repository);

		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null)
		{
			mRepositoryPresenter.setRepository((Repository) getArguments().getSerializable(ARGS_REPOSITORY));
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_repository_details, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		ButterKnife.bind(this, view);

		mLikeImageButton.setOnClickListener(likeImageButton -> mRepositoryLikesPresenter.toggleLike(mRepository.getId()));
	}

	public void setRepository(Repository repository)
	{
		mRepositoryPresenter.setRepository(repository);
	}

	@Override
	public void updateLikes(List<Integer> inProgress, List<Integer> likedIds)
	{
		mRepositoryPresenter.updateLikes(inProgress, likedIds);
	}

	@Override
	public void showRepository(Repository repository)
	{
		mRepository = repository;

		mTitleTextView.setText(repository.getName());
	}

	@Override
	public void updateLike(boolean isInProgress, boolean isLiked)
	{
		mLikeImageButton.setEnabled(!isInProgress);
		mLikeImageButton.setSelected(isLiked);
	}
}
