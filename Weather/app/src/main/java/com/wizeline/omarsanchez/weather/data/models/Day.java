package com.wizeline.omarsanchez.weather.data.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by omarsanchez on 7/26/17.
 */

public class Day {
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    int dt;
    Temp temp;
    List<Weather> weather;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getDate() {
        return df.format(dt*1000L);
    }

    public String getWeatherDescription() {
        return weather.get(0).description;
    }


}
