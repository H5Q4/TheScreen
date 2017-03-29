package com.github.jupittar.core.movies;

import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.core.base.BasePresenter;
import com.github.jupittar.core.base.UiContract;

import java.util.List;

public interface MoviesUiContract {

  interface View extends UiContract.View {
    void showMovies(List<Movie> movies);
  }

  interface Presenter<V extends View> extends UiContract.Presenter<V> {
    void listMovies(int page);
  }

}
