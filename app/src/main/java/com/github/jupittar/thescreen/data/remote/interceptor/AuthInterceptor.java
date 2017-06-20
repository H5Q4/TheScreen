package com.github.jupittar.thescreen.data.remote.interceptor;

import com.github.jupittar.thescreen.helper.Utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private Utils mAppUtils;
    private String mApiKey;

    public AuthInterceptor(Utils utils, String apiKey) {
        mAppUtils = utils;
        mApiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        HttpUrl newUrl = url.newBuilder()
                .addQueryParameter("api_key", mApiKey)
                .addQueryParameter("language", "en")
                .addQueryParameter("region", mAppUtils.getRegion4Api())
                .build();
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}
