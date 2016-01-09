package com.feresr.weather.presenters.views;

import com.feresr.weather.models.CityWeather;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface ForecastView extends View {
    void addForecast(CityWeather l);
}
