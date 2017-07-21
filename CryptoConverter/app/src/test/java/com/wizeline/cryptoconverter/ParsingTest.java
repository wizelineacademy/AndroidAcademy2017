package com.wizeline.cryptoconverter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.wizeline.cryptoconverter.data.model.cryptocompare.CoinsResponse;
import com.wizeline.cryptoconverter.data.model.cryptocompare.ConversionResponse;

import org.junit.Test;

import java.io.InputStream;

import okio.BufferedSource;
import okio.Okio;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class ParsingTest {

    @Test
    public void coinsParsing_isCorrect() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("coins.json");
        BufferedSource source = Okio.buffer(Okio.source(in));

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<CoinsResponse> coinsResponseJsonAdapter = moshi.adapter(CoinsResponse.class);
        CoinsResponse coinsResponse = coinsResponseJsonAdapter.fromJson(source);
        assertTrue(coinsResponse.getCoins().size() > 0);
        assertTrue(coinsResponse.getCoins().size() > 0);
    }


    @Test
    public void conversionParsing_isCorrect() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("conversion.json");
        BufferedSource source = Okio.buffer(Okio.source(in));

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<ConversionResponse> coinsResponseJsonAdapter = moshi.adapter(ConversionResponse.class);
        ConversionResponse conversionResponse = coinsResponseJsonAdapter.fromJson(source);
        assertEquals(1, conversionResponse.getRaw().size());
        assertEquals(1, conversionResponse.getRaw().get("BTC").size());
        assertEquals(1, conversionResponse.getDisplay().size());
        assertEquals(1, conversionResponse.getDisplay().get("BTC").size());
    }
}
