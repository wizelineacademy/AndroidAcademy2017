package com.wizeline.cryptoconverter.presentation.converter;

import android.util.Log;

import com.wizeline.cryptoconverter.data.repo.ConversionRepo;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class ConverterPresenter implements ConverterContract.ConverterPresenter {

    private List<String> coins;
    private ConverterContract.ConverterView converterView;
    private ConversionRepo conversionRepo;

    private Disposable coinsDisposable;
    private Disposable conversionDisposable;

    public ConverterPresenter(ConverterContract.ConverterView converterView, ConversionRepo conversionRepo) {
        this.converterView = converterView;
        this.conversionRepo = conversionRepo;
    }

    @Override public void onCreate() {
        converterView.showLoading();
        coinsDisposable = conversionRepo.getCoinsList()
                .subscribe(coins -> {
                    converterView.hideLoading();
                    converterView.showCoinsList(coins);
                }, throwable -> converterView.showError(throwable.getMessage()));
    }

    @Override public void onDestroy() {
        if (coinsDisposable != null && !coinsDisposable.isDisposed()) {
            coinsDisposable.dispose();
        }
        if (conversionDisposable != null && !conversionDisposable.isDisposed()) {
            conversionDisposable.dispose();
        }
    }

    @Override public void convert(float amount, String from, String to) {
        conversionDisposable = conversionRepo.convert(from, to)
                .subscribe(conversion -> {
                    String result = conversion.getFromSymbol() +
                            " " +
                            NumberFormat.getNumberInstance(Locale.getDefault()).format(amount) +
                            " = " +
                            conversion.getToSymbol() +
                            " " +
                            NumberFormat.getNumberInstance(Locale.getDefault()).format(amount * conversion.getPrice());
                    converterView.showResult(result);
                }, throwable -> {
                    converterView.showError(throwable.getMessage());
                    Log.e("error", throwable.getMessage(), throwable);
                });
    }
}
