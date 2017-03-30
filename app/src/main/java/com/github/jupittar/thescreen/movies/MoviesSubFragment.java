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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.movies.MovieTab;
import com.github.jupittar.core.movies.MoviesUiContract;
import com.github.jupittar.core.util.Constants;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.base.LazyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MoviesSubFragment extends LazyFragment implements MoviesUiContract.View {

    public static final String ARG_PARA_MOVIE_TAB = "movie_tab";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    MoviesUiContract.Presenter<MoviesUiContract.View> mPresenter;
    MoviesAdapter mMoviesAdapter;
    private int mPage;
    private MovieTab mMovieTab;

    public MoviesSubFragment() {
        // Required empty public constructor
    }

    public static MoviesSubFragment newInstance(MovieTab tab) {
        MoviesSubFragment fragment = new MoviesSubFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARA_MOVIE_TAB, tab.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
        mPresenter = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieTab = MovieTab.valueOf(getArguments().getString(ARG_PARA_MOVIE_TAB));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_movies, container, false);
    }

    @Override
    public void onFirstAppear() {
        mPage = 1;
        setUpRecyclerView();
        setUpRefreshLayout();
        mPresenter.showMovies(mMovieTab, mPage);
    }

    private void setUpRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPage = 1;
            mPresenter.showMovies(mMovieTab, mPage);
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
                mPage++;
                mPresenter.showMovies(mMovieTab, mPage);
            }
        });
    }

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {
        appComponent.plus(new AppMoviesModule(this)).inject(this);
    }

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
        Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.addAll(movies);
    }

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
                            Constants.IMAGE_BASE_URL, Constants.POSTER_SIZE, item.getPosterPath()))
                    .centerCrop()
                    .into(posterIv);
        }
    }
}
