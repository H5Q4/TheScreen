package com.github.jupittar.thescreen.moviedetails.info;

import com.github.jupittar.core.moviedetails.info.MovieInfoModule;
import com.github.jupittar.core.moviedetails.info.MovieInfoScope;

import dagger.Subcomponent;

@MovieInfoScope
@Subcomponent(modules = {
        MovieInfoModule.class
})
public interface MovieInfoSubcomponent {
    void inject(MovieInfoFragment fragment);
}
