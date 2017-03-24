package com.github.jupittar.thescreen.ui.popularmovies;

import com.github.jupittar.core.ui.popularmovies.PopularMoviesContract;
import com.github.jupittar.core.ui.popularmovies.PopularMoviesModule;

import dagger.Module;

@Module
public class AppPopularMoviesModule extends PopularMoviesModule {

  public AppPopularMoviesModule(PopularMoviesContract.View view) {
    super(view);
  }
}
