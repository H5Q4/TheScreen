package com.github.jupittar.thescreen.util;

public class Constants {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String API_KEY = "fb1ca2b3ed6f907f369a6ab7d696cf44";

    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_SIZE_W780 = "w780";
    public static final String IMAGE_SIZE_H632 = "h632";

    public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    public static final int NETWORK_READ_TIMEOUT = 10;  // 10 sec
    public static final int NETWORK_WRITE_TIMEOUT = 10; // 10 sec
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int CACHE_MAX_AGE_MINS = 2; // 2 min
    public static final int CACHE_MAX_STALE_DAYS = 7; // 7 day
    public static final int RETRY_COUNT = 3;  // 3 times

    // Event tag
    public static final String EVENT_TAG_REGION_CHANGED = "Event_Region_Changed";
    public static final String EVENT_TAG_CAST_LOADED = "Event_Cast_Loaded";

}
