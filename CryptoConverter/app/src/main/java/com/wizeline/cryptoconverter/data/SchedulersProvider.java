package com.wizeline.cryptoconverter.data;

import io.reactivex.Scheduler;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public interface SchedulersProvider {

    Scheduler mainScheduler();
    Scheduler backgroundScheduler();

}
