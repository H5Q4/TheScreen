package com.github.jupittar.thescreen.ui.movies;

import com.github.jupittar.core.ui.movies.MoviesContract;
import com.github.jupittar.core.ui.movies.MoviesModule;

import dagger.Module;

@Module
public class AppMoviesModule extends MoviesModule {

  public AppMoviesModule(MoviesContract.View view) {
    super(view);
  }
}
