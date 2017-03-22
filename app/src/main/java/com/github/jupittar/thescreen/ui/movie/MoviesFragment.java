package com.github.jupittar.thescreen.ui.movie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.ui.base.BaseFragment;

import java.util.Date;

import butterknife.BindView;

public class MoviesFragment extends BaseFragment {
  private static final String ARG_PARAM_TAB = "category";

  @BindView(R.id.text)
  TextView mTextView;

  private String mTab;


  public MoviesFragment() {
    // Required empty public constructor
  }

  public static MoviesFragment newInstance(MovieTab tab) {
    MoviesFragment fragment = new MoviesFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM_TAB, tab.name());
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mTab = getArguments().getString(ARG_PARAM_TAB);
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mTextView.setText(new Date().getTime()+"");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_movies, container, false);
  }

  public enum MovieTab {
    NOW_PLAYING,
    POPULAR,
    TOP_RATED,
    UPCOMING
  }

}
