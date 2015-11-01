package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.DailyForecast;
import com.feresr.rxweather.models.HourlyForecast;
import com.feresr.rxweather.models.Today;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataSource {
    Observable<DailyForecast> getForecast(String city);
    Observable<HourlyForecast> getTodaysForecast(String city);

    Observable<Today> getTodaysWeather(String city);
}
