package com.github.jupittar.thescreen.helper;

import com.github.jupittar.core.helper.SchedulerHelper;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unused")
public class AppSchedulerHelper implements SchedulerHelper {

  @Override
  public Scheduler mainThread() {
    return AndroidSchedulers.mainThread();
  }

  @Override
  public Scheduler backgroundThread() {
    return Schedulers.io();
  }

}
