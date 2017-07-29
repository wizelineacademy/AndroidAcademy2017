package com.wizeline.cryptoconverter.data.repo.retrofit;

import android.content.Context;

import com.wizeline.cryptoconverter.BuildConfig;
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
public class RetrofitService implements ConversionRepo {

    private CoinsService coinsService;
    private ConversionService conversionService;

    public RetrofitService(Context context) {

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

    @Override public Observable<List<com.wizeline.cryptoconverter.data.model.Conversion>> getTopConversions(String to) {
        return coinsService
                .getCoins()
                .flatMap(coinsResponse -> Observable.fromIterable(coinsResponse.getCoins().values()))
                .sorted((coin, t1) -> coin.getSortOrder() - t1.getSortOrder())
                .take(BuildConfig.MAX_TOP)
                .collect((Callable<List<Coin>>) ArrayList::new, List::add)
                .flatMapObservable(new Function<List<Coin>, Observable<List<com.wizeline.cryptoconverter.data.model.Conversion>>>() {
                    @Override public Observable<List<com.wizeline.cryptoconverter.data.model.Conversion>> apply(@NonNull List<Coin> coins) throws Exception {
                        return convert(coins, to);
                    }
                });
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
                    List<Conversion> conversions = new ArrayList<>();
                    for (String from : conversionResponse.getRaw().keySet()) {
                        Map<String, ConversionResponse.Conversion> rawConversions = conversionResponse.getRaw().get(from);
                        Map<String, ConversionResponse.Conversion> displayConversions = conversionResponse.getDisplay().get(from);
                        for (String to1 : rawConversions.keySet()) {
                            String fromSymbol = displayConversions.get(to1).getFrom();
                            String fromUrl = getCoinUrl(coins, from);

                            String toSymbol = displayConversions.get(to1).getTo();
                            String toUrl = String.format("http://s.xe.com/themes/xe/images/flags/big/%s.png", to1.toLowerCase());

                            String price = displayConversions.get(to1).getPrice();
                            double change = Double.parseDouble(rawConversions.get(to1).getChange());

                            Conversion conversion = new Conversion(fromSymbol, fromUrl, toSymbol, toUrl, change, price);
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

    @Override public Observable<Conversion> convert(String from, String to) {
        return conversionService.convert(from.toUpperCase(), to.toUpperCase())
                .flatMap(response -> {
                    double change = Double.parseDouble(response.getRaw().get(from).get(to).getChange());
                    String fromSymbol = response.getDisplay().get(from).get(to).getFrom();

                    String toSymbol = response.getDisplay().get(from).get(to).getTo();

                    String price = response.getDisplay().get(from).get(to).getPrice();
                    return Observable.just(new Conversion(fromSymbol, null, toSymbol, null, change, price));
                });
    }
}
