package com.wizeline.cryptoconverter;

import com.wizeline.cryptoconverter.data.repo.retrofit.CoinsService;
import com.wizeline.cryptoconverter.data.repo.retrofit.ConversionService;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class RetrofitTest {

    @Test
    public void coinsRequest_isCorrect() throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        new Retrofit.Builder()
                .baseUrl(BuildConfig.COIN_SERVICE_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CoinsService.class)
                .getCoins()
                .subscribe(coinsResponse -> {
                    latch.countDown();
                    assertTrue(coinsResponse.getCoins().size() > 0);
                }, e -> fail());

        latch.await();
    }

    @Test
    public void conversionRequest_isCorrect() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        new Retrofit.Builder()
                .baseUrl(BuildConfig.CONVERSION_SERVICE_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ConversionService.class)
                .convert("BTC,ETH", "MXN")
                .subscribe(conversion -> {
                    latch.countDown();
                    assertEquals(2, conversion.getDisplay().size());
                    assertEquals(1, conversion.getDisplay().get("BTC").size());
                }, e -> fail());

        latch.await();
    }
}
