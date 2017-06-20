package com.github.jupittar.thescreen.helper;

import android.content.Context;

import com.github.jupittar.thescreen.util.AppSchedulerProvider;
import com.github.jupittar.thescreen.util.AppUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HelperModule {

    @Provides
    @Singleton
    public SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    public Utils provideUtils(Context context) {
        return new AppUtils(context);
    }

}
