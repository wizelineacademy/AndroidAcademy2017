package com.wizeline.cryptoconverter;

import android.app.Application;

import com.wizeline.cryptoconverter.data.repo.ConversionRepo;
import com.wizeline.cryptoconverter.data.repo.retrofit.RetrofitService;

/**
 * Created by Nicole Terc on 7/27/17.
 */

public class ConverterApplication extends Application {
    //Singletons
    private static ConversionRepo conversionRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        conversionRepo = new RetrofitService(this);
    }

    public static ConversionRepo getConversionRepo(){
        return conversionRepo;
    }
}
