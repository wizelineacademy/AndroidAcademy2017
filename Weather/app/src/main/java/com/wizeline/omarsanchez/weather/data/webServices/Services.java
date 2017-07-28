package com.wizeline.omarsanchez.weather.data.webServices;

import com.wizeline.omarsanchez.weather.data.models.Forecast;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by omarsanchez on 7/26/17.
 */

public interface Services {
    @GET(" ")
    Observable<Forecast> getForecast(@Query("q") String place, @Query("units") String unit, @Query("cnt") int days, @Query("appid") String apiKey);

}
