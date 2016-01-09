package com.feresr.weather.repository;

import com.feresr.weather.models.City;

import java.util.List;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Repository {
    Observable<City> getForecast(City city);
    Observable<List<City>> getCities();
    Observable<City> saveCity(City city);

    Observable<City> removeCity(City city);
}
