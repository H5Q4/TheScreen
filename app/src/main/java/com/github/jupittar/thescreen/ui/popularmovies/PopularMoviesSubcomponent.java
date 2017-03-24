package com.github.jupittar.thescreen.ui.popularmovies;


import com.github.jupittar.core.ui.popularmovies.PopularMoviesScope;

import dagger.Subcomponent;

@PopularMoviesScope
@Subcomponent(modules = {
    AppPopularMoviesModule.class
})
public interface PopularMoviesSubcomponent {
  void inject(PopularMoviesFragment fragment);
}
