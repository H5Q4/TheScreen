package com.github.jupittar.thescreen.screen.movies;


import dagger.Subcomponent;

@MoviesScope
@Subcomponent(modules = {
        MoviesModule.class
})
public interface MoviesSubcomponent {
    void inject(MoviesByTabFragment fragment);
}
