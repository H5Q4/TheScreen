package com.github.jupittar.thescreen.helper;

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

}
