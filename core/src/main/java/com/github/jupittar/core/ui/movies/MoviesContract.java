package com.github.jupittar.core.ui.movies;

import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.core.ui.base.BasePresenter;
import com.github.jupittar.core.ui.base.Contract;

import java.util.List;

public interface MoviesContract {

  interface View extends Contract.View {
    void showNowPlayingMovies(List<Movie> movies);
  }

  abstract class Presenter<V extends View> extends BasePresenter<V> {
    public abstract void showNowPlayingMovies(int page);
  }

}
