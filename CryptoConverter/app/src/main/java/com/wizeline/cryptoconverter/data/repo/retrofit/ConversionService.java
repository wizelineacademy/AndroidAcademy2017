package com.wizeline.cryptoconverter.data.repo.retrofit;

import com.wizeline.cryptoconverter.data.model.cryptocompare.ConversionResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public interface ConversionService {

    @GET("/data/pricemultifull")
    Observable<ConversionResponse> convert(@Query("fsyms") String from, @Query("tsyms") String to);

}
