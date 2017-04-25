package com.github.jupittar.thescreen.data;

import com.github.jupittar.core.data.local.CacheManager;
import com.github.jupittar.core.data.model.MoviesWrapper;
import com.github.jupittar.thescreen.BuildConfig;
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

    @Provides
    @Singleton
    public CacheManager<MoviesWrapper> provideMoviesCacheManager(
            DualCache<MoviesWrapper> dualCache) {
        return new AppCacheManager<>(dualCache);
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
