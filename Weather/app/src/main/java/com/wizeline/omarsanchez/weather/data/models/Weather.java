package com.wizeline.omarsanchez.weather.data.models;

/**
 * Created by omarsanchez on 7/26/17.
 */

public class Weather {
    String main;
    String description;
    String icon;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
