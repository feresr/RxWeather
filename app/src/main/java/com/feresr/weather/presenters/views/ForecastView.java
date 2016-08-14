package com.feresr.weather.presenters.views;

import com.feresr.weather.common.BaseView;
import com.feresr.weather.models.CityWeather;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface ForecastView extends BaseView {
    void addForecast(CityWeather l);
}
