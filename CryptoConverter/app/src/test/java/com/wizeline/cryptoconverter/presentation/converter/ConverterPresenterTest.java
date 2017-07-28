package com.wizeline.cryptoconverter.presentation.converter;

import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.data.repo.ConversionRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class ConverterPresenterTest {

    private ConverterContract.ConverterPresenter converterPresenter;
    @Mock ConversionRepo conversionRepo;
    @Mock ConverterContract.ConverterView converterView;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        converterPresenter = new ConverterPresenter(converterView, conversionRepo);
    }

    @Test
    public void onCreate() throws Exception {
        List<String> emptyList = new ArrayList<>();
        when(conversionRepo.getCoinsList()).thenReturn(Observable.just(emptyList));
        converterPresenter.onCreate();
        verify(converterView, times(1)).showLoading();
        verify(converterView, times(1)).hideLoading();
        verify(converterView, times(1)).showCoinsList(emptyList);

        when(conversionRepo.getCoinsList()).thenReturn(Observable.error(new Throwable("This is an exception")));
        converterPresenter.onCreate();
        verify(converterView, times(2)).showLoading();
        verify(converterView, times(2)).hideLoading();
        verify(converterView, times(1)).showFullScreenError("This is an exception");

    }

    @Test
    public void convert() throws Exception {
        List<String> emptyList = new ArrayList<>();
        when(conversionRepo.convert("BTC", "MXN")).thenReturn(Observable.just(new Conversion("BTC", null, "MXN", null, 10d, "", 10)));
        converterPresenter.convert(10, "BTC", "MXN");
        verify(converterView, times(1)).showResult("BTC 10 = MXN 100");
    }

}