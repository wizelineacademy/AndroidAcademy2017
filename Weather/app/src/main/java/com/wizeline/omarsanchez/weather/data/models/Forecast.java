package com.wizeline.omarsanchez.weather.data.models;

import java.util.List;

/**
 * Created by omarsanchez on 7/26/17.
 */

public class Forecast {
    City city;
    String cod;
    int count;
    List<Day> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Day> getList() {
        return list;
    }

    public void setList(List<Day> list) {
        this.list = list;
    }
}
