package com.github.jupittar.thescreen.movies;

import com.github.jupittar.core.movies.MoviesModule;
import com.github.jupittar.core.movies.MoviesUiContract;

import dagger.Module;

@Module
public class AppMoviesModule extends MoviesModule {

    public AppMoviesModule(MoviesUiContract.View view) {
        super(view);
    }
}
