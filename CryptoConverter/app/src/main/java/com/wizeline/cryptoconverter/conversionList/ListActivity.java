package com.wizeline.cryptoconverter.conversionList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wizeline.cryptoconverter.ConverterApplication;
import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.data.model.Conversion;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter adapter;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        Observable<List<Conversion>> baseObservable = ConverterApplication.getConversionRepo().getTopConversions("mxn");
        loadData(getSortedList(getFilterNegative(getOneAndTenObservable(baseObservable))));
    }

    private void init() {
        //Init adapter
        adapter = new ListAdapter();

        //Init recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private Observable<List<Conversion>> getFilterNegative(Observable<List<Conversion>> observable) {
        return observable
                .flatMap(Observable::fromIterable)
                .filter(conversion -> conversion.getChange() > 0).toList()
                .toObservable();
    }

    private Observable<List<Conversion>> getSortedList(Observable<List<Conversion>> observable) {
        return observable
                .flatMap(Observable::fromIterable)
                .toSortedList((conversion, t1) -> {
                    char firstLetter = conversion.getFromSymbol().charAt(0);
                    char secondLetter = t1.getFromSymbol().charAt(0);
                    if (firstLetter > secondLetter) {
                        return 1;
                    } else if (firstLetter < secondLetter) {
                        return -1;
                    }
                    return 0;
                }).toObservable();
    }

    private Observable<List<Conversion>> getOneAndTenObservable(Observable<List<Conversion>> observable) {
        return observable
                .flatMap(Observable::fromIterable)
                .flatMap(conversion -> {
                    String fromTen = conversion.getFromSymbol() + "x10";
                    Double newPrice = conversion.getPrice() * 10;
                    String newPriceDisplay = String.format("%s %.2f", conversion.getToSymbol(), newPrice);
                    Conversion conversionTen = new Conversion(
                            fromTen,
                            conversion.getFromImageUrl(),
                            conversion.getToSymbol(),
                            conversion.getToImageUrl(),
                            conversion.getChange(),
                            newPriceDisplay,
                            newPrice
                    );
                    return Observable.fromArray(conversion, conversionTen);
                })
                .toSortedList((conversion, t1) ->
                        String.CASE_INSENSITIVE_ORDER.compare(conversion.getFromSymbol(), t1.getFromSymbol()))
                .toObservable();
    }

    private void loadData(Observable<List<Conversion>> observable) {
        disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Conversion>>() {
                    @Override
                    public void onNext(@NonNull List<Conversion> conversions) {
                        adapter.setItems(conversions);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}