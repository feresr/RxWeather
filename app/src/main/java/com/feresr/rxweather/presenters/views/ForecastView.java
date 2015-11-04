package com.feresr.rxweather.presenters.views;

import com.feresr.rxweather.models.CityWeather;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface ForecastView extends View {
    void addForecast(CityWeather l);
}
