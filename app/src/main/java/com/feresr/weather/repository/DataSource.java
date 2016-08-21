package com.feresr.weather.repository;

import com.feresr.weather.models.City;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataSource {
    Observable<City> getForecast(City city);

    Observable<City> getCities();
}
