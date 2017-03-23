package com.github.jupittar.thescreen.ui.movie;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.AutoFitRecyclerView;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.ui.base.LazyFragment;

import butterknife.BindView;

public class MovieByTabSubFragment extends LazyFragment {

  private static final String ARG_PARAM_TAB = "movie_tab";

  @BindView(R.id.recycler_view)
  AutoFitRecyclerView mRecyclerView;

  private String mMovieTab;


  public MovieByTabSubFragment() {
    // Required empty public constructor
  }

  public static MovieByTabSubFragment newInstance(MoviesFragment.MovieTab tab) {
    MovieByTabSubFragment fragment = new MovieByTabSubFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM_TAB, tab.name());
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mMovieTab = getArguments().getString(ARG_PARAM_TAB);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_movie_by_tab_sub, container, false);
  }

  @Override
  public void onFirstAppear() {
    setUpRecyclerView();
  }

  private void setUpRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    MoviesAdapter adapter = new MoviesAdapter(getActivity(), R.layout.item_movie);
    mRecyclerView.setAdapter(adapter);
  }

  private class MoviesAdapter extends CommonViewAdapter<Movie> {

    public MoviesAdapter(Context context, @LayoutRes int layoutId) {
      super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Movie item) {

    }
  }
}
