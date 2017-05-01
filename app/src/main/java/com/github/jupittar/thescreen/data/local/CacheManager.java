package com.github.jupittar.thescreen.data.local;

import com.vincentbrison.openlibraries.android.dualcache.DualCache;

public class CacheManager<T> {

    private DualCache<T> mDualCache;

    public CacheManager(DualCache<T> dualCache) {
        mDualCache = dualCache;
    }

    public void put(String key, T val) {
        mDualCache.put(key, val);
    }

    public T get(String key) {
        return mDualCache.get(key);
    }
}
