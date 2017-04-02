package com.github.jupittar.thescreen.movies;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.PagingInfo;
import com.github.jupittar.core.movies.MovieTab;
import com.github.jupittar.core.movies.MoviesUiContract;
import com.github.jupittar.core.util.Constants;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.LazyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.mateware.snacky.Snacky;
import es.dmoral.toasty.Toasty;

public class MoviesByTabFragment extends LazyFragment implements MoviesUiContract.View {

    //region Fields
    public static final String ARG_PARAM_MOVIE_TAB = "movie_tab";

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.ll_error) LinearLayout mErrorLayout;

    @Inject
    MoviesUiContract.Presenter<MoviesUiContract.View> mPresenter;
    MoviesAdapter mMoviesAdapter;
    private MovieTab mMovieTab;
    private PagingInfo mPagingInfo;
    //endregion

    //region Constructors
    public MoviesByTabFragment() {
        // Required empty public constructor
    }
    //endregion

    //region Factory Methods
    public static MoviesByTabFragment newInstance(MovieTab tab) {
        MoviesByTabFragment fragment = new MoviesByTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_MOVIE_TAB, tab.name());
        fragment.setArguments(bundle);
        return fragment;
    }
    //endregion

    //region Lifecycle Methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieTab = MovieTab.valueOf(getArguments().getString(ARG_PARAM_MOVIE_TAB));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_by_tab, container, false);
    }

    @Override
    public void onFirstAppear() {
        setUpRecyclerView();
        setUpRefreshLayout();
        mPresenter.showMovies(mMovieTab, 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
        mPresenter = null;
    }
    //endregion

    //region SetUp Methods
    private void setUpRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.showMovies(mMovieTab, 1);
        });
    }

    private void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(getActivity(), R.layout.item_movie);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                mPagingInfo.nextPage();
                mPresenter.showMovies(mMovieTab, mPagingInfo.getCurrentPage());
            }
        });
    }

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {
        appComponent.plus(new AppMoviesModule(this)).inject(this);
    }
    //endregion

    //region Implementation of MoviesUiContract.View
    @Override
    public void showLoading() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorMessage() {
        Toasty.error(getActivity(), "Error Occurred").show();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.addAll(movies);
    }

    @Override
    public void addNoMoreMoviesFooter() {

    }

    @Override
    public boolean isMoviesEmpty() {
        return mMoviesAdapter.isEmpty();
    }

    @Override
    public void showErrorLayout() {
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorLayout() {
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showReloadSnackbar() {
        Snacky.builder()
                .setActivty(getActivity())
                .setText("Network Error")
                .setActionText("Reload")
                .setActionClickListener(
                        v -> mPresenter.showMovies(mMovieTab, mPagingInfo.getCurrentPage()))
                .setDuration(Snacky.LENGTH_INDEFINITE)
                .error()
                .show();
    }

    @Override
    public void updatePagingInfo(PagingInfo pagingInfo) {
        mPagingInfo = pagingInfo;
    }

    @Override
    public void clearMovies() {
        mMoviesAdapter.clear();
    }
    //endregion

    //region RecyclerView's Adapter
    private class MoviesAdapter extends CommonViewAdapter<Movie> {

        MoviesAdapter(Context context, @LayoutRes int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convertView(CommonViewHolder holder, Movie item) {
            AspectRatioImageView posterIv = holder.getView(R.id.iv_movie_poster);
            posterIv.setAspectRatio(1 / AspectRatioImageView.PHI);
            Glide.with(mContext)
                    .load(String.format("%s%s%s",
                            Constants.IMAGE_BASE_URL,
                            Constants.POSTER_SIZE,
                            item.getPosterPath()))
                    .centerCrop()
                    .into(posterIv);
        }
    }
    //endregion
}
