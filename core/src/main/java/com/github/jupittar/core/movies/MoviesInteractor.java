package com.github.jupittar.core.movies;

import com.github.jupittar.core.data.local.CacheManager;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.MoviesWrapper;
import com.github.jupittar.core.data.model.PagingInfo;
import com.github.jupittar.core.data.model.RawResponse;
import com.github.jupittar.core.data.remote.TmdbService;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class MoviesInteractor {

    private static final String CACHE_KEY_FORMAT = "movies_%s_%d";

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
        String cacheKey = String.format(Locale.ENGLISH, CACHE_KEY_FORMAT, tab.name(), page);
        return Observable
                .concat(getLocalMovies(cacheKey), getRemoteMovies(tab, page))
                .firstElement();
    }

    private Observable<MoviesWrapper> getRemoteMovies(MovieTab tab, int page) {
        String cacheKey = String.format(Locale.ENGLISH, CACHE_KEY_FORMAT, tab.name(), page);
        Observable<RawResponse<Movie>> rawResponseObservable;
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
            default:
                rawResponseObservable = Observable.empty();
        }
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
                        mCacheManager.put(cacheKey, moviesWrapper));
    }

    private Observable<MoviesWrapper> getLocalMovies(String cacheKey) {
        return Observable
                .fromCallable(() -> mCacheManager.get(cacheKey))
                .filter(moviesWrapper -> moviesWrapper != null && !isPastOneDay(moviesWrapper))
                .onErrorResumeNext(Observable.empty());
    }

    private boolean isPastOneDay(MoviesWrapper moviesWrapper) {
        long currentTime = new Date().getTime();
        return currentTime - moviesWrapper.getCreatedTime() > 24 * 60 * 60 * 1000;
    }

}
