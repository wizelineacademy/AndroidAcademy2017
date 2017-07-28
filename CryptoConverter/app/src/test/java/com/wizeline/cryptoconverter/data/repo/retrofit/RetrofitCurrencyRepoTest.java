package com.wizeline.cryptoconverter.data.repo.retrofit;

import android.content.Context;

import com.wizeline.cryptoconverter.data.SchedulersProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class RetrofitCurrencyRepoTest {

    private RetrofitCurrencyRepo retrofitCurrencyRepo;

    @Before
    public void setup() {
        retrofitCurrencyRepo = new RetrofitCurrencyRepo(mock(Context.class), new SchedulersProvider() {
            @Override public Scheduler mainScheduler() {
                return Schedulers.io();
            }

            @Override public Scheduler backgroundScheduler() {
                return Schedulers.io();
            }
        });
    }

    @Test
    public void getTopConversions() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        retrofitCurrencyRepo.getTopConversions("mxn").subscribe(conversions -> {
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
        CountDownLatch latch = new CountDownLatch(1);
        retrofitCurrencyRepo.convert("btc", "mxn").subscribe(conversion -> {
            assertEquals("$", conversion.getToSymbol());
            latch.countDown();
        }, e -> {
            latch.countDown();
            fail();
        });
        latch.await();
    }

}