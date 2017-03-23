package com.github.jupittar.core.helper;


import io.reactivex.Scheduler;

@SuppressWarnings("unused")
public interface SchedulerHelper {

  Scheduler mainThread();

  Scheduler backgroundThread();

}
