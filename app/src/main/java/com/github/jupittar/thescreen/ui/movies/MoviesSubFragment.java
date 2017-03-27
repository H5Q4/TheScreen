package com.github.jupittar.thescreen.ui.movies;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.jupittar.commlib.custom.AspectRatioImageView;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.core.ui.movies.MoviesContract;
import com.github.jupittar.core.util.Constants;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.ui.base.LazyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MoviesSubFragment extends LazyFragment implements MoviesContract.View {

  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;

  @Inject
  MoviesContract.Presenter<MoviesContract.View> mPresenter;

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
    mPresenter = null;
  }

  MoviesAdapter mMoviesAdapter;

  public MoviesSubFragment() {
    // Required empty public constructor
  }

  public static MoviesSubFragment newInstance() {
    MoviesSubFragment fragment = new MoviesSubFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_sub_movies, container, false);
  }

  @Override
  public void onFirstAppear() {
    setUpRecyclerView();
    mPresenter.showNowPlayingMovies(1);
  }

  private void setUpRecyclerView() {
    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);
    mMoviesAdapter = new MoviesAdapter(getActivity(), R.layout.item_movie);
    mRecyclerView.setAdapter(mMoviesAdapter);
  }

  @Override
  protected void injectDependencies(Context context, AppComponent appComponent) {
    appComponent.plus(new AppMoviesModule(this)).inject(this);
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  @Override
  public void showErrorMessage() {

  }

  @Override
  public void showNowPlayingMovies(List<Movie> movies) {
    mMoviesAdapter.addAll(movies);
  }

  private class MoviesAdapter extends CommonViewAdapter<Movie> {

    MoviesAdapter(Context context, @LayoutRes int layoutId) {
      super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Movie item) {
      AspectRatioImageView posterIv = holder.getView(R.id.iv_movie_poster);
      posterIv.setAspectRatio(1/AspectRatioImageView.PHI);
      Glide.with(mContext)
          .load(String.format("%s%s%s",
              Constants.IMAGE_BASE_URL, Constants.POSTER_SIZE, item.getPosterPath()))
          .centerCrop()
          .into(posterIv);
    }
  }
}
