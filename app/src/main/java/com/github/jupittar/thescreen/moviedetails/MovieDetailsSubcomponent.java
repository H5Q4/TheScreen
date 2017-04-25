package com.github.jupittar.thescreen.moviedetails;

import com.github.jupittar.core.moviedetails.MovieDetailsModule;

import dagger.Subcomponent;

@Subcomponent(modules = {
        MovieDetailsModule.class
})
public interface MovieDetailsSubcomponent {
    void inject(MovieDetailsActivity activity);
}
