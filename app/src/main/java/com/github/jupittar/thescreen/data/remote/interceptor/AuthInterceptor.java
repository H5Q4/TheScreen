package com.github.jupittar.thescreen.data.remote.interceptor;

import android.content.Context;

import com.github.jupittar.commlib.util.AndroidUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("unused")
public class AuthInterceptor implements Interceptor {

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
                .addQueryParameter("region", AndroidUtils.getLocale(mContext).getCountry())
                .build();
        Request newRequest = request.newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}
