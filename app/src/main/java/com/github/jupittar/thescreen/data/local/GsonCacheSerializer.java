package com.github.jupittar.thescreen.data.local;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vincentbrison.openlibraries.android.dualcache.CacheSerializer;

import java.lang.reflect.Type;

public class GsonCacheSerializer<T> implements CacheSerializer<T> {

    private Gson mGson;
    private Class<T> mClazz;
    private Type mType;

    public GsonCacheSerializer(Class<T> clazz) {
        mGson = new Gson();
        mClazz = clazz;
    }

    public GsonCacheSerializer(Type type) {
        mGson = new Gson();
        mType = type;
    }

    @Override
    public T fromString(String data) {
        try {
            if (mClazz != null) {
                return mGson.fromJson(data, mClazz);
            }
            if (mType != null) {
                return mGson.fromJson(data, mType);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    @Override
    public String toString(T object) {
        try {
            return mGson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
