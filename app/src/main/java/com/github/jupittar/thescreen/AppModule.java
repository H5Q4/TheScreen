package com.github.jupittar.thescreen;

import android.content.Context;
import android.content.res.Resources;

import com.github.jupittar.commlib.util.AppUtils;

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

  @Provides
  @Singleton
  @Named("cacheDir")
  File provideCacheDir(Context context) {
    return context.getCacheDir();
  }

}
