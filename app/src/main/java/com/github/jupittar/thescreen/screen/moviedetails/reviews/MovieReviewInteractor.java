package com.github.jupittar.thescreen.screen.moviedetails.reviews;


import com.github.jupittar.thescreen.data.entity.PagingInfo;
import com.github.jupittar.thescreen.data.entity.ReviewWrapper;
import com.github.jupittar.thescreen.data.remote.TmdbService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MovieReviewInteractor implements MovieReviewContract.interactor {

    private TmdbService mTmdbService;

    @Inject
    public MovieReviewInteractor(TmdbService tmdbService) {
        mTmdbService = tmdbService;
    }

    @Override
    public Observable<ReviewWrapper> getReviews(long movieId) {
        return mTmdbService.getReviews(movieId)
                .filter(reviewRawResponse -> reviewRawResponse.getResults() != null)
                .map(reviewRawResponse -> new ReviewWrapper(reviewRawResponse.getResults(),
                        new PagingInfo(reviewRawResponse.getPage(), reviewRawResponse.getTotalPages()),
                        System.currentTimeMillis()));
    }
}
