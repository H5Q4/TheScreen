package com.github.jupittar.thescreen.data.remote;

import android.content.Context;

import com.github.jupittar.thescreen.data.remote.interceptor.AuthInterceptor;
import com.github.jupittar.thescreen.data.remote.interceptor.HttpCacheInterceptor;
import com.github.jupittar.thescreen.data.remote.interceptor.HttpLoggingInterceptor;
import com.github.jupittar.thescreen.data.remote.interceptor.HttpOfflineCacheInterceptor;
import com.github.jupittar.thescreen.data.remote.interceptor.RetryInterceptor;
import com.github.jupittar.thescreen.util.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@SuppressWarnings("unused")
public class NetworkModule {

    @Provides
    @Singleton
    public TmdbService provideTMDbService(Retrofit retrofit) {
        return retrofit.create(TmdbService.class);
    }

    //region Retrofit
    @Provides
    @Singleton
    public Retrofit provideRetrofit(
            GsonConverterFactory converterFactory,
            RxJava2CallAdapterFactory callAdapterFactory,
            OkHttpClient okHttpClient
    ) {
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }

    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory provideRxjava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
    //endregion

    //region OkHttpClient
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(
            @Named("isDebug") boolean isDebug,
            Cache cache,
            HttpLoggingInterceptor loggingInterceptor,
            AuthInterceptor authInterceptor,
            HttpCacheInterceptor cacheInterceptor
//            ,HttpOfflineCacheInterceptor offlineInterceptor
    ) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Constants.NETWORK_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
//                .addInterceptor(offlineInterceptor)
                .cache(cache);

        if (isDebug) {
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public Cache provideCache(
            @Named("cacheDir") File cacheDir
    ) {
        Cache cache = null;
        try {
            cache = new Cache(new File(cacheDir.getPath(), "http"), Constants.CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @Singleton
    public AuthInterceptor provideAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Provides
    @Singleton
    public HttpCacheInterceptor provideCacheInterceptor() {
        return new HttpCacheInterceptor(Constants.CACHE_MAX_AGE_MINS);
    }

    @Provides
    @Singleton
    public HttpOfflineCacheInterceptor provideOfflineCacheInterceptor(Context context) {
        return new HttpOfflineCacheInterceptor(context, Constants.CACHE_MAX_STALE_DAYS);
    }

    @Provides
    @Singleton
    public RetryInterceptor provideRetryInterceptor() {
        return new RetryInterceptor(Constants.RETRY_COUNT);
    }
    //endregion

}
