package com.feresr.rxweather.models.wrappers;

import com.feresr.rxweather.models.Weather;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Fernando on 17/10/2015.
 */
public class ListaWrapper extends RealmObject{

    private RealmList<WeatherWrapper> weather = new RealmList<>();

    public RealmList<WeatherWrapper> getWeather() {
        return weather;
    }

    public void setWeather(RealmList<WeatherWrapper> weather) {
        this.weather = weather;
    }
}
