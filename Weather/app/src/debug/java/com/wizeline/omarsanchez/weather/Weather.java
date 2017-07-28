package com.wizeline.omarsanchez.weather;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by omarsanchez on 7/28/17.
 */

public class Weather extends Application {
    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
