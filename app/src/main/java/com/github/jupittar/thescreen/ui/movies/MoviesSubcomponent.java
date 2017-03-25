package com.github.jupittar.thescreen.ui.movies;


import com.github.jupittar.core.ui.movies.MoviesScope;

import dagger.Subcomponent;

@MoviesScope
@Subcomponent(modules = {
    AppMoviesModule.class
})
public interface MoviesSubcomponent {
  void inject(MoviesSubFragment fragment);
}
