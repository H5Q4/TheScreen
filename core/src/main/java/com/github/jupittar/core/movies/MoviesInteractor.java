package com.github.jupittar.core.movies;

import com.github.jupittar.core.data.local.CacheManager;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.MoviesWrapper;
import com.github.jupittar.core.data.model.PagingInfo;
import com.github.jupittar.core.data.model.RawResponse;
import com.github.jupittar.core.data.remote.TmdbService;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class MoviesInteractor {

    private static final String KEY_CACHE_MOVIES = "key_cache_movies";

    private TmdbService mTmdbService;
    private CacheManager<MoviesWrapper> mCacheManager;

    @Inject
    public MoviesInteractor(
            TmdbService tmdbService,
            CacheManager<MoviesWrapper> cacheManager) {
        mTmdbService = tmdbService;
        mCacheManager = cacheManager;
    }

    Maybe<MoviesWrapper> loadMovies(MovieTab tab, int page) {
        Observable<RawResponse<Movie>> responseObservable = getMoviesByTab(tab, page);
        Observable<MoviesWrapper> cachedMoviesObservable = getCachedMovies();
        Observable<MoviesWrapper> remoteMoviesObservable = getRemoteMovies(responseObservable);
        return Observable
                .concat(cachedMoviesObservable, remoteMoviesObservable)
                .firstElement();
    }

    private Observable<RawResponse<Movie>> getMoviesByTab(MovieTab tab, int page) {
        Observable<RawResponse<Movie>> rawResponseObservable = null;
        switch (tab) {
            case NOW_PLAYING:
                rawResponseObservable = mTmdbService.getNowPlayingMovies(page);
                break;
            case POPULAR:
                rawResponseObservable = mTmdbService.getPopularMovies(page);
                break;
            case TOP_RATED:
                rawResponseObservable = mTmdbService.getTopRatedMovies(page);
                break;
            case UPCOMING:
                rawResponseObservable = mTmdbService.getUpcomingMovies(page);
                break;
        }
        return rawResponseObservable;
    }

    private Observable<MoviesWrapper> getRemoteMovies(
            Observable<RawResponse<Movie>> rawResponseObservable) {
        return rawResponseObservable
                .timestamp()
                .map(rawResponseTimed -> {
                    RawResponse<Movie> movieRawResponse = rawResponseTimed.value();
                    return new MoviesWrapper(movieRawResponse.getResults(),
                            new PagingInfo(movieRawResponse.getPage(),
                                    movieRawResponse.getTotalPages()),
                            rawResponseTimed.time());
                })
                .filter(moviesWrapper -> moviesWrapper != null)
                .doOnNext(moviesWrapper ->
                        mCacheManager.put(KEY_CACHE_MOVIES, moviesWrapper));
    }

    private Observable<MoviesWrapper> getCachedMovies() {
        return Observable
                .fromCallable(() -> mCacheManager.get(KEY_CACHE_MOVIES))
                .filter(moviesWrapper -> moviesWrapper != null && !isPastOneDay(moviesWrapper))
                .onErrorResumeNext(Observable.empty());
    }

    private boolean isPastOneDay(MoviesWrapper moviesWrapper) {
        long currentTime = new Date().getTime();
        return currentTime - moviesWrapper.getCreatedTime() > 24 * 60 * 60 * 1000;
    }

}
