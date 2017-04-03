package com.github.jupittar.thescreen.movies;

import com.github.jupittar.core.movies.MoviesContract;
import com.github.jupittar.core.movies.MoviesModule;

import dagger.Module;

@Module
public class AppMoviesModule extends MoviesModule {

    public AppMoviesModule(MoviesContract.View view) {
        super(view);
    }
}
