package com.wizeline.omarsanchez.weather.data;

import com.wizeline.omarsanchez.weather.data.webServices.RetrofitApi;

/**
 * Created by omarsanchez on 7/26/17.
 */

public class DataProvider {
    private OnDataProvided onDataProvided;
    private RetrofitApi retrofitApi;

    public DataProvider(OnDataProvided onDataProvided) {
        this.onDataProvided = onDataProvided;
        this.retrofitApi = new RetrofitApi();
    }

    public void getForecast(String city, String unit, int days) {
        onDataProvided.forecastProvided(retrofitApi.getForecast(city, unit, days));
    }


}
