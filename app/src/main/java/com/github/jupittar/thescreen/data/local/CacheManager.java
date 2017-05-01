package com.github.jupittar.thescreen.data.local;

public interface CacheManager<T> {

    void put(String key, T val);

    T get(String key);

}
