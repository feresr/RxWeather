package com.feresr.rxweather.repository;


import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.storage.DataCache;

import rx.Observable;


/**
 * Created by Fernando on 16/10/2015.
 */
public class DiskDataSource implements DataSource {
    private DataCache cache;

    public DiskDataSource(DataCache cache) {
        this.cache = cache;
    }

    @Override
    public Observable<CityWeather> getForecast(String cityId, String lat, String lon) {
        return cache.getForecast(cityId);
    }

    @Override
    public Observable<City> getCities() {
        return cache.getCities();
    }

    public Observable<City> saveCity(String id, String name, Double lat, Double lon) {
        return cache.putCity(id, name, lat, lon);
    }
}
