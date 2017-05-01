package com.github.jupittar.thescreen.data;

import com.github.jupittar.thescreen.data.local.CacheManager;
import com.vincentbrison.openlibraries.android.dualcache.DualCache;

public class AppCacheManager<T> implements CacheManager<T> {

    private DualCache<T> mDualCache;

    public AppCacheManager(DualCache<T> dualCache) {
        mDualCache = dualCache;
    }

    @Override
    public void put(String key, T val) {
        mDualCache.put(key, val);
    }

    @Override
    public T get(String key) {
        return mDualCache.get(key);
    }
}
