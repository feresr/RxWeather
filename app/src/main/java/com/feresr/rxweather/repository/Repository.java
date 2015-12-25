package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

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
