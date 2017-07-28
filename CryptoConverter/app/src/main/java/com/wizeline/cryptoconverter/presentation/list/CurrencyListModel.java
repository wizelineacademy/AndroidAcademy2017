package com.wizeline.cryptoconverter.presentation.list;

import com.wizeline.cryptoconverter.data.model.Conversion;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Miguel Villaseñor on 7/28/17.
 */
public class CurrencyListModel implements Serializable {

    private final String toCoin;
    private final List<Conversion> conversions;

    public CurrencyListModel(String toCoin, List<Conversion> conversions) {
        this.toCoin = toCoin;
        this.conversions = conversions;
    }

    public String getToCoin() {
        return toCoin;
    }

    public List<Conversion> getConversions() {
        return conversions;
    }
}
