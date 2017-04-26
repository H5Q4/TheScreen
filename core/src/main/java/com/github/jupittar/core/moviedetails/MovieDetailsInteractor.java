package com.github.jupittar.core.moviedetails;

import com.github.jupittar.core.data.local.CacheManager;
import com.github.jupittar.core.data.model.ImagesWrapper;
import com.github.jupittar.core.data.remote.TmdbService;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MovieDetailsInteractor implements MovieDetailsContract.Interactor {

    public static final String CACHE_KEY_FORMAT = "movie_details_%d";

    private TmdbService mTmdbService;
    private CacheManager<ImagesWrapper> mImagesWrapperCacheManager;

    @Inject
    public MovieDetailsInteractor(TmdbService tmdbService,
                                  CacheManager<ImagesWrapper> imagesWrapperCacheManager) {
        mTmdbService = tmdbService;
        mImagesWrapperCacheManager = imagesWrapperCacheManager;
    }

    @Override
    public Observable<ImagesWrapper> getImages(long movieId) {
        String cacheKey = String.format(Locale.ENGLISH, CACHE_KEY_FORMAT, movieId);
        return Observable
                .concat(getLocalMovieImages(cacheKey), getRemoteMovieImages(movieId))
                .firstElement()
                .toObservable();
    }

    //region Private Helper Methods
    private Observable<ImagesWrapper> getLocalMovieImages(String cacheKey) {
        return Observable
                .fromCallable(() -> mImagesWrapperCacheManager.get(cacheKey))
                .filter(imagesWrapper -> imagesWrapper != null && !imagesWrapper.isExpired())
                .onErrorResumeNext(Observable.empty());
    }

    private Observable<ImagesWrapper> getRemoteMovieImages(long movieId) {
        String cacheKey = String.format(Locale.ENGLISH, CACHE_KEY_FORMAT, movieId);
        return mTmdbService
                .getMovieImages(movieId)
                .filter(images -> images != null)
                .map(images -> new ImagesWrapper(images, System.currentTimeMillis()))
                .doOnNext(imagesWrapper -> mImagesWrapperCacheManager.put(cacheKey, imagesWrapper));
    }
    //endregion

}
