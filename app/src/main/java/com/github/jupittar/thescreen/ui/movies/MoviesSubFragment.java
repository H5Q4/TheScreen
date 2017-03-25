package com.github.jupittar.thescreen.ui.movies;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.AutoFitRecyclerView;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.ui.base.LazyFragment;

import butterknife.BindView;

public class MoviesSubFragment extends LazyFragment {

  @BindView(R.id.recycler_view)
  AutoFitRecyclerView mRecyclerView;


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
    return inflater.inflate(R.layout.fragment_popular_movies, container, false);
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

  @Override
  protected void injectDependencies(AppComponent appComponent) {
    
  }

  private class MoviesAdapter extends CommonViewAdapter<Movie> {

    MoviesAdapter(Context context, @LayoutRes int layoutId) {
      super(context, layoutId);
    }

    @Override
    public void convertView(CommonViewHolder holder, Movie item) {

    }
  }
}
