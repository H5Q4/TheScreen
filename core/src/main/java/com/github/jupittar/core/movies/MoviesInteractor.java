package com.github.jupittar.core.movies;

import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.MoviesWrapper;
import com.github.jupittar.core.data.model.PagingInfo;
import com.github.jupittar.core.data.model.RawResponse;
import com.github.jupittar.core.data.remote.TmdbService;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesInteractor {

    private TmdbService mTmdbService;

    @Inject
    public MoviesInteractor(TmdbService TmdbService) {
        mTmdbService = TmdbService;
    }

    public Single<MoviesWrapper> loadMovies(MovieTab tab, int page) {
        Single<RawResponse<Movie>> movieSingle = null;
        switch (tab) {
            case NOW_PLAYING:
                movieSingle = mTmdbService.getNowPlayingMovies(page);
                break;
            case POPULAR:
                movieSingle = mTmdbService.getPopularMovies(page);
                break;
            case TOP_RATED:
                movieSingle = mTmdbService.getTopRatedMovies(page);
                break;
            case UPCOMING:
                movieSingle = mTmdbService.getUpcomingMovies(page);
                break;
        }
        return movieSingle.map(movieRawResponse -> new MoviesWrapper(movieRawResponse.getResults(),
                new PagingInfo(movieRawResponse.getPage(), movieRawResponse.getTotalPages())));
    }

}
