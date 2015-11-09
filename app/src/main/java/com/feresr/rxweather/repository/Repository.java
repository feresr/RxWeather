package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import java.util.List;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Repository {
    Observable<CityWeather> getForecast(String lat, String lon, String cityId);
    Observable<List<City>> getCities();
    Observable<City> saveCity(String id, String name, Double lat, Double lon);

    Observable<City> removeCity(City city);
}
