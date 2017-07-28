package com.wizeline.omarsanchez.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wizeline.omarsanchez.weather.data.DataProvider;
import com.wizeline.omarsanchez.weather.data.OnDataProvided;
import com.wizeline.omarsanchez.weather.data.models.Day;
import com.wizeline.omarsanchez.weather.data.models.Forecast;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

public class MainActivity extends AppCompatActivity implements OnDataProvided {
    DataProvider dataProvider;
    ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataProvider = new DataProvider(this);
        adapter = new ForecastAdapter(new ArrayList<Day>());
        RecyclerView list = (RecyclerView) findViewById(R.id.main_recycler);
        list.setAdapter(adapter);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        dataProvider.getForecast(BuildConfig.city, "metric", 14);

    }

    @Override
    public void forecastProvided(Observable<Forecast> observable) {
        observable.subscribe(new Consumer<Forecast>() {
            @Override
            public void accept(@NonNull Forecast forecast) throws Exception {
                result(forecast);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    private void result(Forecast forecast) {
        adapter.dayList = forecast.getList();
        setTitle(forecast.getCity().getName()+", "+forecast.getCity().getCountry());
        adapter.notifyDataSetChanged();
    }
}
