package com.github.jupittar.thescreen.screen.movies;

import com.github.jupittar.thescreen.data.local.CacheManager;
import com.github.jupittar.thescreen.data.model.Movie;
import com.github.jupittar.thescreen.data.model.MoviesWrapper;
import com.github.jupittar.thescreen.data.model.PagingInfo;
import com.github.jupittar.thescreen.data.model.RawResponse;
import com.github.jupittar.thescreen.data.remote.TmdbService;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MoviesInteractor implements MoviesContract.Interactor {

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

    @Override
    public Observable<MoviesWrapper> getMovies(MovieTab tab, int page) {
        String cacheKey = String
                .format(Locale.ENGLISH, CACHE_KEY_FORMAT, tab.name(), page)
                .toLowerCase();
        return Observable
                .concat(getLocalMovies(cacheKey), getRemoteMovies(tab, page))
                .firstElement()
                .toObservable();
    }

    //region Private Helper Methods
    private Observable<MoviesWrapper> getRemoteMovies(MovieTab tab, int page) {
        String cacheKey = String
                .format(Locale.ENGLISH, CACHE_KEY_FORMAT, tab.name(), page)
                .toLowerCase();
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
                .map(movieRawResponse -> new MoviesWrapper(movieRawResponse.getResults(),
                        new PagingInfo(movieRawResponse.getPage(),
                                movieRawResponse.getTotalPages()),
                        System.currentTimeMillis()))
                .filter(moviesWrapper -> moviesWrapper != null)
                .doOnNext(moviesWrapper ->
                        mCacheManager.put(cacheKey, moviesWrapper));
    }

    private Observable<MoviesWrapper> getLocalMovies(String cacheKey) {
        return Observable
                .fromCallable(() -> mCacheManager.get(cacheKey))
                .filter(moviesWrapper -> moviesWrapper != null && !moviesWrapper.isExpired())
                .onErrorResumeNext(Observable.empty());
    }
    //endregion

}
