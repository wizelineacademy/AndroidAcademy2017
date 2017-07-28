package com.wizeline.cryptoconverter.data.repo.retrofit;

import android.content.Context;
import android.util.Log;

import com.wizeline.cryptoconverter.BuildConfig;
import com.wizeline.cryptoconverter.data.SchedulersProvider;
import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.data.model.cryptocompare.Coin;
import com.wizeline.cryptoconverter.data.model.cryptocompare.ConversionResponse;
import com.wizeline.cryptoconverter.data.repo.ConversionRepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class RetrofitCurrencyRepo implements ConversionRepo {

    private CoinsService coinsService;
    private ConversionService conversionService;
    private SchedulersProvider schedulersProvider;

    public RetrofitCurrencyRepo(Context context, SchedulersProvider schedulersProvider) {

        this.schedulersProvider = schedulersProvider;

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(new File(context.getCacheDir(), "responses"), 10 * 1024 * 1024))
                .build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client);

        coinsService = retrofitBuilder
                .baseUrl(BuildConfig.COIN_SERVICE_BASE)
                .build()
                .create(CoinsService.class);

        conversionService = retrofitBuilder
                .baseUrl(BuildConfig.CONVERSION_SERVICE_BASE)
                .build()
                .create(ConversionService.class);

    }

    @Override
    public Observable<List<Conversion>> getTopConversions(String to) {
        return coinsService
                .getCoins()
                .flatMap(coinsResponse -> Observable.fromIterable(coinsResponse.getCoins().values()))
                .sorted((coin, t1) -> coin.getSortOrder() - t1.getSortOrder())
                .take(10)
                .collect((Callable<List<Coin>>) ArrayList::new, List::add)
                .flatMapObservable(new Function<List<Coin>, Observable<List<com.wizeline.cryptoconverter.data.model.Conversion>>>() {
                    @Override public Observable<List<com.wizeline.cryptoconverter.data.model.Conversion>> apply(@NonNull List<Coin> coins) throws Exception {
                        return convert(coins, to);
                    }
                })
                .subscribeOn(schedulersProvider.backgroundScheduler())
                .observeOn(schedulersProvider.mainScheduler());
    }

    @Override
    public Observable<List<String>> getCoinsList() {
        return coinsService
                .getCoins()
                .flatMap(coinsResponse -> Observable.fromIterable(coinsResponse.getCoins().values()))
                .sorted((coin, t1) -> coin.getSortOrder() - t1.getSortOrder())
                .map(Coin::getName)
                .collect((Callable<List<String>>) ArrayList::new, List::add)
                .flatMapObservable(Observable::just)
                .subscribeOn(schedulersProvider.backgroundScheduler())
                .observeOn(schedulersProvider.mainScheduler());
    }

    @Override
    public Observable<Conversion> convert(String from, String to) {

        return conversionService.convert(from.toUpperCase(), to.toUpperCase())
                .flatMap(response -> {
                    if(response.getMessage() != null ) {
                        throw new Error(response.getMessage());
                    }
                    String toUpper = to.toUpperCase();
                    String fromUpper = from.toUpperCase();
                    double change = Double.parseDouble(response.getRaw().get(fromUpper).get(toUpper).getChange());
                    String fromSymbol = response.getDisplay().get(fromUpper).get(toUpper).getFrom();

                    String toSymbol = response.getDisplay().get(fromUpper).get(toUpper).getTo();

                    String priceDisplay = response.getDisplay().get(fromUpper).get(toUpper).getPrice();
                    double price = Double.parseDouble(response.getRaw().get(fromUpper).get(toUpper).getPrice());
                    return Observable.just(new Conversion(fromSymbol, null, toSymbol, null, change, priceDisplay, price));
                })
                .subscribeOn(schedulersProvider.backgroundScheduler())
                .observeOn(schedulersProvider.mainScheduler());
    }

    private Observable<List<com.wizeline.cryptoconverter.data.model.Conversion>> convert(List<Coin> coins, String to) {
        StringBuilder stringBuilder = new StringBuilder();

        String sep = "";
        for (Coin coin : coins) {
            stringBuilder.append(sep);
            stringBuilder.append(coin.getName());
            sep = ",";
        }

        return conversionService.convert(stringBuilder.toString(), to.toUpperCase())
                .flatMap(conversionResponse -> {
                    if(conversionResponse.getMessage() != null ) {
                        throw new Error(conversionResponse.getMessage());
                    }
                    List<Conversion> conversions = new ArrayList<>();
                    for (String from : conversionResponse.getRaw().keySet()) {
                        Map<String, ConversionResponse.Conversion> rawConversions = conversionResponse.getRaw().get(from);
                        Map<String, ConversionResponse.Conversion> displayConversions = conversionResponse.getDisplay().get(from);
                        for (String to1 : rawConversions.keySet()) {
                            String fromSymbol = displayConversions.get(to1).getFrom();
                            String fromUrl = getCoinUrl(coins, from);

                            String toSymbol = displayConversions.get(to1).getTo();
                            String toUrl = String.format("http://s.xe.com/themes/xe/images/flags/big/%s.png", to1.toLowerCase());

                            String priceDisplay = displayConversions.get(to1).getPrice();
                            double change = Double.parseDouble(rawConversions.get(to1).getChange());
                            double price = Double.parseDouble(rawConversions.get(to1).getPrice());

                            Conversion conversion = new Conversion(fromSymbol, fromUrl, toSymbol, toUrl, change, priceDisplay, price);
                            conversions.add(conversion);
                        }
                    }
                    return Observable.just(conversions);
                });
    }

    private String getCoinUrl(List<Coin> coins, String coin) {
        for (Coin item : coins) {
            if (item.getName().equals(coin.toUpperCase())) {
                return "https://www.cryptocompare.com" + item.getImageUrl();
            }
        }
        return null;
    }
}
