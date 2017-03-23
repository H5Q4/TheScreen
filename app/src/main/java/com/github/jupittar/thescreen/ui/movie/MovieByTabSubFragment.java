package com.github.jupittar.thescreen.ui.movie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.thescreen.R;

public class MovieByTabSubFragment extends Fragment {

  private static final String ARG_PARAM_TAB = "movie_tab";

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

}
