package com.github.jupittar.thescreen.screen.moviedetails.reviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jupittar.commlib.recyclerview.BaseViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.data.remote.response.Review;
import com.github.jupittar.thescreen.screen.base.LazyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;


public class MovieReviewFragment extends LazyFragment implements MovieReviewContract.View {

    private static final String ARG_PARAM_MOVIE_ID = "movie_id";

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading) ContentLoadingProgressBar mLoadingProgressBar;

    @Inject MovieReviewContract.Presenter<MovieReviewContract.View> mPresenter;
    private ReviewAdapter mAdapter;
    private long mMovieId;

    public MovieReviewFragment() {
    }

    public static MovieReviewFragment newInstance(long movieId) {
        final MovieReviewFragment fragment = new MovieReviewFragment();
        final Bundle bundle = new Bundle();
        bundle.putLong(ARG_PARAM_MOVIE_ID, movieId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getLong(ARG_PARAM_MOVIE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_review, container, false);
    }

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {
        appComponent.plus(new MovieReviewModule(this)).inject(this);
    }


    @Override
    public void onDestroyView() {
        mPresenter.detach();
        mPresenter = null;
        super.onDestroyView();
    }

    @Override
    public void onFirstAppear() {
        setUpRecyclerView();
        mPresenter.showReviews(mMovieId);
    }

    private void setUpRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ReviewAdapter(getContext(), R.layout.item_movie_review);
        mRecyclerView.setAdapter(mAdapter);
    }

    //region implementation of MovieReviewContract.View
    @Override
    public void showLoading() {
        mLoadingProgressBar.show();
    }

    @Override
    public void hideLoading() {
        mLoadingProgressBar.hide();
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Toasty.error(getContext(), throwable.getMessage()).show();
    }

    @Override
    public void showReviews(List<Review> reviews) {
        mAdapter.addAll(reviews);
    }

    //endregion

    private class ReviewAdapter extends CommonViewAdapter<Review> {

        public ReviewAdapter(Context context, @LayoutRes int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convertView(BaseViewHolder holder, Review item) {
            TextView authorTv = holder.getView(R.id.tv_review_author);
            TextView contentTv = holder.getView(R.id.tv_review_content);

            authorTv.setText(item.getAuthor());
            contentTv.setText(item.getContent());
        }
    }
}
