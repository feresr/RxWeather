package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import java.util.List;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataSource {
    Observable<City> getForecast(City city);
    Observable<List<City>> getCities();
}
