package com.feresr.weather.storage;



import com.feresr.weather.models.City;
import com.feresr.weather.models.CityWeather;

import java.util.List;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataCache {
    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired(City city);

    /**
     * Evict all elements of the cache.
     */
    void evictAll();

    void putForecast(String cityId, CityWeather forecast);

    Observable<CityWeather> getForecast(String cityId);

    Observable<List<City>> getCities();

    Observable<City> putCity(City city);

    Observable removeCity(City city);
}
