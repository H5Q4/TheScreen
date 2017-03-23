package com.github.jupittar.thescreen;

import android.content.Context;
import android.content.res.Resources;

import com.github.jupittar.commlib.util.AppUtils;
import com.github.jupittar.core.helper.AndroidHelper;
import com.github.jupittar.core.helper.LoggerHelper;
import com.github.jupittar.core.helper.SchedulerHelper;
import com.github.jupittar.thescreen.helper.AppAndroidHelper;
import com.github.jupittar.thescreen.helper.AppLoggerHelper;
import com.github.jupittar.thescreen.helper.AppSchedulerHelper;
import com.github.jupittar.thescreen.util.TMDbApiConfigurationUtils;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private TheScreenApp mApp;

  AppModule(TheScreenApp app) {
    this.mApp = app;
  }

  @Provides
  @Singleton
  @Named("secureBaseUrl")
  String provideSecureBaseUrl(Context context) {
    return TMDbApiConfigurationUtils.getSecureBaseUrl(context);
  }

  @Provides
  @Singleton
  @Named("posterSize")
  String providePosterSize(Context context) {
    return TMDbApiConfigurationUtils.getPosterSize(context);
  }

  @Provides
  @Singleton
  @Named("cacheDir")
  File provideCacheDir(Context context) {
    return context.getCacheDir();
  }

  @Provides
  @Singleton
  @Named("isDebug")
  boolean isDebug() {
    return AppUtils.isDebug();
  }

  @Provides
  @Singleton
  Context provideContext() {
    return mApp.getApplicationContext();
  }

  @Provides
  @Singleton
  Resources provideResource() {
    return mApp.getResources();
  }

}
