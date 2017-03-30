package com.github.jupittar.thescreen.helper;

import android.content.Context;

import com.github.jupittar.core.helper.AndroidHelper;
import com.github.jupittar.core.helper.LoggerHelper;
import com.github.jupittar.core.helper.SchedulerHelper;

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
