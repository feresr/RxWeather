package com.feresr.rxweather.storage;



import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

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
    boolean isExpired(String cityId);

    /**
     * Evict all elements of the cache.
     */
    void evictAll();

    void putForecast(String cityId, CityWeather forecast);

    Observable<CityWeather> getForecast(String cityId);

    Observable<City> getCities();

    Observable<City> putCity(String id, String name, Double lat, Double lon);
}
