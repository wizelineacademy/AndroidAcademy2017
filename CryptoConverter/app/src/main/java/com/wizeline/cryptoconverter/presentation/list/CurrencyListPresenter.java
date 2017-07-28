package com.wizeline.cryptoconverter.presentation.list;

import com.wizeline.cryptoconverter.data.repo.ConversionRepo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class CurrencyListPresenter implements CurrencyListContract.CurrencyListPresenter {

    private CurrencyListContract.CurrencyListView view;
    private ConversionRepo conversionRepo;
    private CurrencyListModel currencyListModel;

    private Disposable disposable;

    public CurrencyListPresenter(CurrencyListContract.CurrencyListView view, ConversionRepo conversionRepo) {
        this.view = view;
        this.conversionRepo = conversionRepo;
        currencyListModel = new CurrencyListModel("MXN", null);
    }

    @Override public void onCreate() {
        fetchList();
    }

    @Override public void onDestroy() {
        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override public void fetchList() {
        view.showLoading();
        disposable = conversionRepo.getTopConversions(currencyListModel.getToCoin())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.hideLoading();
                    currencyListModel = new CurrencyListModel(currencyListModel.getToCoin(), list);
                    view.showList(currencyListModel.getConversions());
                }, throwable -> {
                    view.hideLoading();
                    view.showError("Error fetching conversion list.");
                });
    }

    @Override public void setCurrency(String currency) {
        currencyListModel = new CurrencyListModel(currency, currencyListModel.getConversions());
        fetchList();
    }
}
