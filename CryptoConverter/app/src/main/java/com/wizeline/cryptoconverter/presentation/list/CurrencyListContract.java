package com.wizeline.cryptoconverter.presentation.list;

import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.presentation.base.Presenter;

import java.util.List;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class CurrencyListContract {

    public interface CurrencyListView {
        void showLoading();
        void hideLoading();
        void showError(String error);
        void showList(List<Conversion> conversionList);
    }

    public interface CurrencyListPresenter extends Presenter {
        void fetchList();
        void setCurrency(String currency);
    }

}

