package com.github.jupittar.thescreen.helper;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler ui();

    Scheduler computation();

}
