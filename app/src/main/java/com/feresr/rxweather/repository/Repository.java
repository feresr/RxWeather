package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.Day;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Repository {
    Observable<Day> getForecast(String cityName);
}
