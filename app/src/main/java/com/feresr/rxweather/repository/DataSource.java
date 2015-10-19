package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.Forecast;
import com.feresr.rxweather.models.Today;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataSource {
    Observable<Forecast> getForecast(String city);

    Observable<Today> getTodaysWeather(String city);
}
