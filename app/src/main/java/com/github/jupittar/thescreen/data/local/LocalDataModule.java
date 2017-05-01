package com.github.jupittar.thescreen.data.local;

import com.github.jupittar.thescreen.BuildConfig;
import com.github.jupittar.thescreen.data.model.ImagesWrapper;
import com.github.jupittar.thescreen.data.model.MoviesWrapper;
import com.vincentbrison.openlibraries.android.dualcache.Builder;
import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataModule {

    private static final String CACHE_ID_MOVIES = "movies";
    private static final String CACHE_ID_MOVIE_DETAILS = "movie_details";

    @Provides
    @Singleton
    public CacheManager<ImagesWrapper> provideMovieDetailsCacheManager(
            DualCache<ImagesWrapper> dualCache) {
        return new CacheManager<>(dualCache);
    }

    @Provides
    @Singleton
    public DualCache<ImagesWrapper> provideMovieDetailsCache(
            @Named("cacheDir") File cacheDir,
            @Named("diskCacheSize") int diskCacheSize,
            @Named("ramCacheSize") int ramCacheSize) {
        GsonCacheSerializer<ImagesWrapper> serializer = new GsonCacheSerializer<>(
                ImagesWrapper.class);
        return new Builder<ImagesWrapper>(CACHE_ID_MOVIE_DETAILS, BuildConfig.VERSION_CODE)
                .useSerializerInRam(ramCacheSize, serializer)
                .useSerializerInDisk(diskCacheSize, cacheDir, serializer)
                .build();
    }

    @Provides
    @Singleton
    public CacheManager<MoviesWrapper> provideMoviesCacheManager(
            DualCache<MoviesWrapper> dualCache) {
        return new CacheManager<>(dualCache);
    }

    @Provides
    @Singleton
    public DualCache<MoviesWrapper> provideMoviesCache(
            @Named("cacheDir") File cacheDir,
            @Named("diskCacheSize") int diskCacheSize,
            @Named("ramCacheSize") int ramCacheSize) {
        GsonCacheSerializer<MoviesWrapper> serializer = new GsonCacheSerializer<>(
                MoviesWrapper.class);
        return new Builder<MoviesWrapper>(CACHE_ID_MOVIES, BuildConfig.VERSION_CODE)
                .useSerializerInRam(ramCacheSize, serializer)
                .useSerializerInDisk(diskCacheSize, cacheDir, serializer)
                .build();
    }

}
