package com.github.jupittar.core.moviedetails;

import com.github.jupittar.core.data.model.Images;

import io.reactivex.Maybe;

public class MovieDetailsInteractor implements MovieDetailsContract.Interactor {

    @Override
    public Maybe<Images> getImages(long movieId) {
        return null;
    }
}
