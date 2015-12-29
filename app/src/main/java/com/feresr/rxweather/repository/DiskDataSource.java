package com.feresr.rxweather.repository;


import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.storage.DataCache;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;


/**
 * Created by Fernando on 16/10/2015.
 */
public class DiskDataSource implements DataSource {
    private DataCache cache;

    public DiskDataSource(DataCache cache) {
        this.cache = cache;
    }

    @Override
    public Observable<City> getForecast(final City city) {
        return Observable.just(city);
    }

    @Override
    public Observable<List<City>> getCities() {
        return cache.getCities();
    }

    public Observable<City> saveCity(City city) {
        return cache.putCity(city);
    }

    public Observable<City> removeCity(City city) {
        return cache.removeCity(city);
    }
}
