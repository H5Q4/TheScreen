package com.github.jupittar.thescreen.helper;


import io.reactivex.Scheduler;

@SuppressWarnings("unused")
public interface SchedulerHelper {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
