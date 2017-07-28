package com.wizeline.cryptoconverter;

import android.app.Application;

import com.wizeline.cryptoconverter.data.SchedulersProvider;
import com.wizeline.cryptoconverter.data.repo.ConversionRepo;
import com.wizeline.cryptoconverter.data.repo.retrofit.RetrofitCurrencyRepo;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nicole Terc on 7/27/17.
 */

public class ConverterApplication extends Application {
    //Singletons
    private static ConversionRepo conversionRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        conversionRepo = new RetrofitCurrencyRepo(this, new SchedulersProvider() {
            @Override public Scheduler mainScheduler() {
                return AndroidSchedulers.mainThread();
            }

            @Override public Scheduler backgroundScheduler() {
                return Schedulers.io();
            }
        });
    }

    public static ConversionRepo getConversionRepo(){
        return conversionRepo;
    }
}
