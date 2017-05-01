package com.github.jupittar.thescreen.screen.moviedetails.info;

import dagger.Subcomponent;

@MovieInfoScope
@Subcomponent(modules = {
        MovieInfoModule.class
})
public interface MovieInfoSubcomponent {
    void inject(MovieInfoFragment fragment);
}
