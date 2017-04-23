package com.github.jupittar.thescreen.movies;


import com.github.jupittar.core.movies.MoviesModule;
import com.github.jupittar.core.movies.MoviesScope;

import dagger.Subcomponent;

@MoviesScope
@Subcomponent(modules = {
        MoviesModule.class
})
public interface MoviesSubcomponent {
    void inject(MoviesByTabFragment fragment);
}
