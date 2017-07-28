package com.wizeline.cryptoconverter.presentation.list;

import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.data.repo.ConversionRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class CurrencyListPresenterTest {

    @Mock CurrencyListContract.CurrencyListView view;
    @Mock ConversionRepo conversionRepo;
    CurrencyListContract.CurrencyListPresenter currencyListPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        currencyListPresenter = new CurrencyListPresenter(view, conversionRepo);
    }

    @Test
    public void onCreate() throws Exception {
        List<Conversion> emptyList = new ArrayList<>();
        when(conversionRepo.getTopConversions("MXN"))
                .thenReturn(Observable.just(emptyList));
        currencyListPresenter.onCreate();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideLoading();
        verify(view, times(1)).showList(emptyList);
        verify(conversionRepo, times(1)).getTopConversions("MXN");
    }

    @Test
    public void fetchList() throws Exception {
        List<Conversion> emptyList = new ArrayList<>();
        when(conversionRepo.getTopConversions("MXN"))
                .thenReturn(Observable.just(emptyList));
        currencyListPresenter.onCreate();
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideLoading();
        verify(view, times(1)).showList(emptyList);
        verify(conversionRepo, times(1)).getTopConversions("MXN");

        when(conversionRepo.getTopConversions("MXN"))
                .thenReturn(Observable.error(new Exception("This is an exception")));
        currencyListPresenter.onCreate();
        verify(view, times(2)).showLoading();
        verify(view, times(2)).hideLoading();
        verify(view, times(1)).showError("This is an exception");
        verify(conversionRepo, times(2)).getTopConversions("MXN");
    }

    @Test
    public void setCurrency() throws Exception {
        List<Conversion> emptyList = new ArrayList<>();
        when(conversionRepo.getTopConversions("USD"))
                .thenReturn(Observable.just(emptyList));

        currencyListPresenter.setCurrency("USD");
        verify(view, times(1)).showLoading();
        verify(view, times(1)).hideLoading();
        verify(view, times(1)).showList(emptyList);
        verify(conversionRepo, times(1)).getTopConversions("USD");

    }

}