package com.wizeline.cryptoconverter.conversionList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.wizeline.cryptoconverter.ConverterApplication;
import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.cryptoConverter.ConverterActivity;
import com.wizeline.cryptoconverter.data.model.Conversion;

import java.util.List;

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
        loadData();
    }

    private void init() {
        //Init adapter
        adapter = new ListAdapter();

        //Init recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.currency_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.converter:
                startActivity(new Intent(this, ConverterActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
