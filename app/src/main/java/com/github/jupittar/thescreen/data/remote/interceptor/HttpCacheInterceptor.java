package com.github.jupittar.thescreen.data.remote.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

@SuppressWarnings("unused")
public class HttpCacheInterceptor implements Interceptor {

    private int mCacheMaxAgeMins;

    public HttpCacheInterceptor(int cacheMaxAgeMins) {
        mCacheMaxAgeMins = cacheMaxAgeMins;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(mCacheMaxAgeMins, TimeUnit.MINUTES)
                .build();
        return response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", cacheControl.toString())
                // if your server doesn't support cache header, uncomment below to replace the upper line
                // may work(not tested!)
//        .header("Cache-Control", "public, max-age=" + Constants.CACHE_MAX_AGE_MINS * 60)
                .build();
    }
}
