package com.wizeline.omarsanchez.weather.data;

import com.wizeline.omarsanchez.weather.data.models.Forecast;

import io.reactivex.Observable;

/**
 * Created by omarsanchez on 7/26/17.
 */

public interface OnDataProvided {
     void forecastProvided(Observable<Forecast> observable);
}
