package com.github.jupittar.thescreen.screen.moviedetails.info;

import com.github.jupittar.thescreen.data.entity.MovieDetailsWrapper;
import com.github.jupittar.thescreen.data.remote.TmdbService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MovieInfoInteractor implements MovieInfoContract.Interactor {

    private TmdbService mTmdbService;

    @Inject
    public MovieInfoInteractor(TmdbService tmdbService) {
        mTmdbService = tmdbService;
    }

    @Override
    public Observable<MovieDetailsWrapper> getMovieDetails(long movieId) {
        return Observable.zip(
                mTmdbService.getMovieDetails(movieId),
                mTmdbService.getMovieCredits(movieId),
                mTmdbService.getSimilarMovies(movieId),
                (movie, credits, movieRawResponse) ->
                        new MovieDetailsWrapper(movie, credits, movieRawResponse.getResults())
        );
    }

}
