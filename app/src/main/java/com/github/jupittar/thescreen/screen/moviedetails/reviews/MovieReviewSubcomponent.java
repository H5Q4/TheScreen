package com.github.jupittar.thescreen.screen.moviedetails.reviews;

import dagger.Subcomponent;

@MovieReviewScope
@Subcomponent(modules = {
        MovieReviewModule.class
})
public interface MovieReviewSubcomponent {
    void inject(MovieReviewFragment fragment);
}
