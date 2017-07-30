package com.wizeline.cryptoconverter.data.repo;

import com.wizeline.cryptoconverter.data.model.Conversion;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public interface ConversionRepo {

    Observable<List<Conversion>> getTopConversions(String to);
    Observable<Conversion> convert(String from, String to);
    Observable<List<String>> getCoinsList();

}
