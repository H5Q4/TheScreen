package com.github.jupittar.thescreen.data.remote.interceptor;

import android.content.Context;

import com.github.jupittar.thescreen.helper.Utils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("unused")
public class AuthInterceptor implements Interceptor {

    @Inject Utils mAppUtils;

    private Context mContext;
    private String mApiKey;

    public AuthInterceptor(Context context, String apiKey) {
        mContext = context;
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
