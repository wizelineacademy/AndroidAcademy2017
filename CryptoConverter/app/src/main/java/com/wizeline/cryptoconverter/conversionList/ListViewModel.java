package com.wizeline.cryptoconverter.conversionList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wizeline.cryptoconverter.ConverterApplication;
import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.mvvm.ViewModel;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nicole Terc on 7/27/17.
 */

public class ListViewModel extends ViewModel {

    ListAdapter adapter;
    Disposable disposable;
    RecyclerView.LayoutManager layoutManager;
    Context context;

    public ListViewModel(@Nullable State savedInstanceState, Context context) {
        super(savedInstanceState);
        this.context = context;

        adapter = new ListAdapter();
        layoutManager = new LinearLayoutManager(context);

        if (savedInstanceState instanceof ListViewModelState) {
            ListViewModelState viewModelState = (ListViewModelState) savedInstanceState;
            adapter.setItems(viewModelState.items);
            layoutManager.onRestoreInstanceState(viewModelState.layoutManagerState);

        } else {
            loadData();
        }
    }

    public ListAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    private void loadData() {
        disposable = ConverterApplication
                .getConversionRepo()
                .getTopConversions("mxn")
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
    public void onDestroy() {
        super.onDestroy();
        context = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    //State
    @Override
    public State getInstanceState() {
        return new ListViewModelState(this);
    }

    private static class ListViewModelState extends State {
        private final Parcelable layoutManagerState;
        private final List<Conversion> items;
        private Type listType = new TypeToken<List<Conversion>>() {
        }.getType();


        public ListViewModelState(ListViewModel viewModel) {
            super(viewModel);
            layoutManagerState = viewModel.layoutManager.onSaveInstanceState();
            items = viewModel.adapter.getItems();
        }

        protected ListViewModelState(Parcel in) {
            super(in);
            layoutManagerState = in.readParcelable(RecyclerView.LayoutManager.class.getClassLoader());
            items = new Gson().fromJson(in.readString(), listType);
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            super.writeToParcel(parcel, flags);
            parcel.writeParcelable(layoutManagerState, flags);
            parcel.writeString(new Gson().toJson(items, listType));
        }
    }


}
