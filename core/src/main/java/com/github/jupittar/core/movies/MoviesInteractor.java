package com.github.jupittar.core.movies;

import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.RawResponse;
import com.github.jupittar.core.data.remote.TMDbService;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesInteractor {

    private TMDbService mTMDbService;

    @Inject
    public MoviesInteractor(TMDbService TMDbService) {
        mTMDbService = TMDbService;
    }

    public Single<RawResponse<Movie>> loadMovies(MovieTab tab, int page) {
        Single<RawResponse<Movie>> movieSingle = null;
        switch (tab) {
            case NOW_PLAYING:
                movieSingle = mTMDbService.getNowPlayingMovies(page);
                break;
            case POPULAR:
                movieSingle = mTMDbService.getPopularMovies(page);
                break;
            case TOP_RATED:
                movieSingle = mTMDbService.getTopRatedMovies(page);
                break;
            case UPCOMING:
                movieSingle = mTMDbService.getUpcomingMovies(page);
                break;
        }
        return movieSingle;
    }

}
