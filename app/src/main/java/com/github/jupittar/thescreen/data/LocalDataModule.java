package com.github.jupittar.thescreen.data;

import com.github.jupittar.core.data.local.CacheManager;
import com.github.jupittar.core.data.model.MoviesWrapper;
import com.github.jupittar.thescreen.BuildConfig;
import com.github.jupittar.thescreen.util.AppConstants;
import com.vincentbrison.openlibraries.android.dualcache.Builder;
import com.vincentbrison.openlibraries.android.dualcache.DualCache;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataModule {

    @Provides
    @Singleton
    public DualCache<MoviesWrapper> provideMoviesCache(@Named("cacheDir") File cacheDir) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int ramCacheSize = maxMemory / 8;
        GsonCacheSerializer<MoviesWrapper> serializer = new GsonCacheSerializer<>(
                MoviesWrapper.class);
        return new Builder<MoviesWrapper>(AppConstants.CACHE_ID, BuildConfig.VERSION_CODE)
                .useSerializerInRam(ramCacheSize, serializer)
                .useSerializerInDisk(AppConstants.DISK_CACHE_SIZE, cacheDir, serializer)
                .build();
    }

    @Provides
    @Singleton
    public CacheManager<MoviesWrapper> provideMoviesCacheManager(
            DualCache<MoviesWrapper> dualCache) {
        return new AppCacheManager<>(dualCache);
    }

}
