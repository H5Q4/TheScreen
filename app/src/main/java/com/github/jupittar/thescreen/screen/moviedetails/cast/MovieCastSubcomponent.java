package com.github.jupittar.thescreen.screen.moviedetails.cast;

import dagger.Subcomponent;

@MovieCastScope
@Subcomponent(modules = {
        MovieCastModule.class
})
public interface MovieCastSubcomponent {
    void inject(MovieCastFragment fragment);
}
