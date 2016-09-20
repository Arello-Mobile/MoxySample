package com.arellomobile.github.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.github.R;
import com.arellomobile.github.mvp.models.Repository;
import com.arellomobile.github.mvp.presenters.RepositoryLikesPresenter;
import com.arellomobile.github.mvp.views.RepositoryLikesView;
import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 22.01.2016
 * Time: 17:04
 *
 * @author Yuri Shmakov
 */
public class RepositoriesAdapter extends MvpBaseAdapter implements RepositoryLikesView {
	public static final int REPOSITORY_VIEW_TYPE = 0;
	public static final int PROGRESS_VIEW_TYPE = 1;

	@InjectPresenter(type = PresenterType.GLOBAL, tag = RepositoryLikesPresenter.TAG)
	RepositoryLikesPresenter mRepositoryLikesPresenter;

	private int mSelection = -1;
	private List<Repository> mRepositories;
	private List<Integer> mLiked;
	private List<Integer> mLikesInProgress;
	private boolean mMaybeMore;
	private OnScrollToBottomListener mScrollToBottomListener;

	public RepositoriesAdapter(MvpDelegate<?> parentDelegate, OnScrollToBottomListener scrollToBottomListener) {
		super(parentDelegate, String.valueOf(0));

		mScrollToBottomListener = scrollToBottomListener;
		mRepositories = new ArrayList<>();
		mLiked = new ArrayList<>();
		mLikesInProgress = new ArrayList<>();
	}

	public void setRepositories(List<Repository> repositories, boolean maybeMore) {
		mRepositories = new ArrayList<>(repositories);
		dataSetChanged(maybeMore);
	}

	public void addRepositories(List<Repository> repositories, boolean maybeMore) {
		mRepositories.addAll(repositories);
		dataSetChanged(maybeMore);
	}

	public void updateLikes(List<Integer> inProgress, List<Integer> likedIds) {
		mLikesInProgress = new ArrayList<>(inProgress);
		mLiked = new ArrayList<>(likedIds);

		notifyDataSetChanged();
	}

	public void setSelection(int selection) {
		mSelection = selection;

		notifyDataSetChanged();
	}

	private void dataSetChanged(boolean maybeMore) {
		mMaybeMore = maybeMore;

		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return position == mRepositories.size() ? PROGRESS_VIEW_TYPE : REPOSITORY_VIEW_TYPE;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public int getRepositoriesCount() {
		return mRepositories.size();
	}

	@Override
	public int getCount() {
		return mRepositories.size() + (mMaybeMore ? 1 : 0);
	}

	@Override
	public Repository getItem(int position) {
		return mRepositories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == PROGRESS_VIEW_TYPE) {
			if (mScrollToBottomListener != null) {
				mScrollToBottomListener.onScrollToBottom();
			}

			return new ProgressBar(parent.getContext());
		}

		RepositoryHolder holder;
		if (convertView != null) {
			holder = (RepositoryHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
			holder = new RepositoryHolder(convertView);
			convertView.setTag(holder);
		}

		final Repository item = getItem(position);

		holder.bind(position, item);

		return convertView;
	}

	class RepositoryHolder {
		View view;
		@Bind(R.id.item_repository_text_view_name)
		TextView nameTextView;
		@Bind(R.id.item_repository_image_button_like)
		ImageButton likeImageButton;

		RepositoryHolder(View view) {
			this.view = view;

			ButterKnife.bind(this, view);
		}

		void bind(int position, Repository repository) {
			view.setBackgroundResource(position == mSelection ? R.color.colorAccent : android.R.color.transparent);

			nameTextView.setText(repository.getName());

			likeImageButton.setOnClickListener(v -> mRepositoryLikesPresenter.toggleLike(repository.getId()));

			boolean isInProgress = mLikesInProgress.contains(repository.getId());

			likeImageButton.setEnabled(!isInProgress);
			likeImageButton.setSelected(mLiked.contains(repository.getId()));
		}
	}

	public interface OnScrollToBottomListener {
		void onScrollToBottom();
	}
}
