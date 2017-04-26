package com.github.jupittar.thescreen.moviedetails;

import com.github.jupittar.core.moviedetails.MovieDetailsModule;
import com.github.jupittar.core.moviedetails.MovieDetailsScope;

import dagger.Subcomponent;

@MovieDetailsScope
@Subcomponent(modules = {
        MovieDetailsModule.class
})
public interface MovieDetailsSubcomponent {
    void inject(MovieDetailsActivity activity);
}
