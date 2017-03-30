package com.github.jupittar.thescreen;

import android.app.Application;

import com.github.jupittar.commlib.util.AppUtils;
import com.github.jupittar.core.data.remote.NetworkModule;
import com.github.jupittar.thescreen.helper.HelperModule;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

public class TheScreenApp extends Application {

    private static final String LOGGER_TAG = "TheScreenApp_Log";

    private static TheScreenApp sInstance;
    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static TheScreenApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppUtils.syncIsDebug(sInstance);
        if (AppUtils.isDebug()) {
            initApp4Debug();
        } else {
            initApp4Release();
        }
        sAppComponent = createAppComponent();
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .helperModule(new HelperModule())
                .networkModule(new NetworkModule())
                .build();
    }

    private void initApp4Release() {
        Logger
                .init()
                .logLevel(LogLevel.NONE);
    }

    private void initApp4Debug() {
        Logger
                .init(LOGGER_TAG)
                .methodCount(3)                 // default 2
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .hideThreadInfo()
                .methodOffset(2);                // default 0
    }

}
