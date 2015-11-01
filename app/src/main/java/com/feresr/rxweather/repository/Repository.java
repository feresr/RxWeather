package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.Day;
import com.feresr.rxweather.models.Hour;
import com.feresr.rxweather.models.Today;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Repository {
    Observable<Day> getForecast(String cityName);
    Observable<Hour> getTodayForecast(String cityName);
    Observable<Today> getTodaysWeather(String cityName);
}
