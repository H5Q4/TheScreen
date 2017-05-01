package com.github.jupittar.thescreen.screen.moviedetails;

import dagger.Subcomponent;

@MovieDetailsScope
@Subcomponent(modules = {
        MovieDetailsModule.class
})
public interface MovieDetailsSubcomponent {
    void inject(MovieDetailsActivity activity);
}
