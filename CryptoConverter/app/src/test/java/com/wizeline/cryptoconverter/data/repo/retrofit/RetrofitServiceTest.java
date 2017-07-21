package com.wizeline.cryptoconverter.data.repo.retrofit;

import android.content.Context;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class RetrofitServiceTest {
    @Test
    public void getTopConversions() throws Exception {
        RetrofitService retrofitService = new RetrofitService(mock(Context.class));
        CountDownLatch latch = new CountDownLatch(1);
        retrofitService.getTopConversions("mxn").subscribe(conversions -> {
            assertEquals(10, conversions.size());
            latch.countDown();
        }, e -> {
            latch.countDown();
            fail();
        });
        latch.await();
    }

    @Test
    public void convert() throws Exception {
        RetrofitService retrofitService = new RetrofitService(mock(Context.class));
        CountDownLatch latch = new CountDownLatch(1);
        retrofitService.convert("btc", "mxn").subscribe(conversion -> {
            assertEquals("$", conversion.getToSymbol());
            latch.countDown();
        }, e -> {
            latch.countDown();
            fail();
        });
        latch.await();
    }

}