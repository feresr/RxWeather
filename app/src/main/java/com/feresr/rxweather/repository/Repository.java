package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Repository {
    Observable<CityWeather> getForecast(String lat, String lon);
    Observable<City> getCities();
}
