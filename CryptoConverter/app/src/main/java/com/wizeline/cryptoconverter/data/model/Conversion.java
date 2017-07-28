package com.wizeline.cryptoconverter.data.model;

/**
 * Created by Miguel Villaseñor on 7/20/17.
 */
public class Conversion {
    String fromSymbol;
    String fromImageUrl;
    String toSymbol;
    String toImageUrl;
    double change;
    String priceDisplay;
    double price;

    public Conversion(String fromSymbol, String fromImageUrl, String toSymbol, String toImageUrl, double change, String priceDisplay, double price) {
        this.fromSymbol = fromSymbol;
        this.fromImageUrl = fromImageUrl;
        this.toSymbol = toSymbol;
        this.toImageUrl = toImageUrl;
        this.change = change;
        this.priceDisplay = priceDisplay;
        this.price = price;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public String getFromImageUrl() {
        return fromImageUrl;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public String getToImageUrl() {
        return toImageUrl;
    }

    public double getChange() {
        return change;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceDisplay() {
        return priceDisplay;
    }
}
