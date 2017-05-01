package com.github.jupittar.thescreen.helper;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HelperModule {

    @Provides
    @Singleton
    LoggerHelper provideLoggerHelper() {
        return new AppLoggerHelper();
    }

    @Provides
    @Singleton
    SchedulerHelper provideAppScheduler() {
        return new AppSchedulerHelper();
    }

    @Provides
    @Singleton
    AndroidHelper provideAndroidHelper(Context context) {
        return new AppAndroidHelper(context);
    }


}
