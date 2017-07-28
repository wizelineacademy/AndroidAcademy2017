package com.wizeline.cryptoconverter.data.model.cryptocompare;

import com.squareup.moshi.Json;

import java.util.Map;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class ConversionResponse {

    @Json(name = "RAW")
    private Map<String, Map<String, Conversion>> raw;
    @Json(name = "DISPLAY")
    private Map<String, Map<String, Conversion>> display;

    @Json(name = "Message")
    private String message;

    public Map<String, Map<String, Conversion>> getRaw() {
        return raw;
    }

    public Map<String, Map<String, Conversion>> getDisplay() {
        return display;
    }

    public String getMessage() {
        return message;
    }

    public static class Conversion {
        @Json(name = "FROMSYMBOL")
        private String from;
        @Json(name = "TOSYMBOL")
        private String to;
        @Json(name = "PRICE")
        private String price;
        @Json(name = "CHANGE24HOUR")
        private String change;

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getPrice() {
            return price;
        }

        public String getChange() {
            return change;
        }
    }


}
