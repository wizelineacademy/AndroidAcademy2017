package com.wizeline.cryptoconverter.presentation.converter;

import com.wizeline.cryptoconverter.presentation.base.Presenter;

import java.util.List;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class ConverterContract {

    public interface ConverterView {
        void showLoading();
        void hideLoading();
        void showCoinsList(List<String> coins);
        void showFullScreenError(String error);
        void showError(String error);
        void showResult(String result);
    }

    public interface ConverterPresenter extends Presenter {
        void convert (float amount, String from, String to);
    }

}
