package com.wizeline.cryptoconverter.data.repo.retrofit;

import com.wizeline.cryptoconverter.data.model.cryptocompare.CoinsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public interface CoinsService {

    @GET("/api/data/coinlist/")
    Observable<CoinsResponse> getCoins();

}
