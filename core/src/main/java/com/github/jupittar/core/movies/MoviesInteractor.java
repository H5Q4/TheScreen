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
import io.reactivex.Single;

public class MoviesInteractor {

    public static final String KEY_CACHE_MOVIES = "key_cache_movies";

    private TmdbService mTmdbService;
    private CacheManager<MoviesWrapper> mCacheManager;

    @Inject
    public MoviesInteractor(TmdbService tmdbService,
                            CacheManager<MoviesWrapper> cacheManager) {
        mTmdbService = tmdbService;
        mCacheManager = cacheManager;
    }

    public Maybe<MoviesWrapper> loadMovies(MovieTab tab, int page) {
        Single<RawResponse<Movie>> remoteMoviesSingle = getMoviesByTab(tab, page);
        Observable<MoviesWrapper> cachedMoviesObservable = getCachedMovies();
        Observable<MoviesWrapper> remoteMoviesObservable = getRemoteMovies(remoteMoviesSingle);
        return Observable
                .concat(cachedMoviesObservable, remoteMoviesObservable)
                .firstElement();
    }

    private Single<RawResponse<Movie>> getMoviesByTab(MovieTab tab, int page) {
        Single<RawResponse<Movie>> remoteMoviesSingle = null;
        switch (tab) {
            case NOW_PLAYING:
                remoteMoviesSingle = mTmdbService.getNowPlayingMovies(page);
                break;
            case POPULAR:
                remoteMoviesSingle = mTmdbService.getPopularMovies(page);
                break;
            case TOP_RATED:
                remoteMoviesSingle = mTmdbService.getTopRatedMovies(page);
                break;
            case UPCOMING:
                remoteMoviesSingle = mTmdbService.getUpcomingMovies(page);
                break;
        }
        return remoteMoviesSingle;
    }

    private Observable<MoviesWrapper> getRemoteMovies(
            Single<RawResponse<Movie>> remoteMoviesSingle) {
        return remoteMoviesSingle
                .toObservable()
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
        return Single
                .fromCallable(() -> mCacheManager.get(KEY_CACHE_MOVIES))
                .toObservable()
                .filter(moviesWrapper -> moviesWrapper != null && !isPastOneDay(moviesWrapper))
                .onErrorResumeNext(Observable.empty());
    }

    private boolean isPastOneDay(MoviesWrapper moviesWrapper) {
        long currentTime = new Date().getTime();
        System.out.println(currentTime);
        System.out.println(moviesWrapper.getCreatedTime());
        return currentTime - moviesWrapper.getCreatedTime() > 24 * 60 * 60 * 1000;
    }

}
