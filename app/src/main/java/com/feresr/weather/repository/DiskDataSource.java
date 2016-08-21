package com.feresr.weather.repository;


import com.feresr.weather.models.City;
import com.feresr.weather.storage.Storage;

import rx.Observable;


/**
 * Created by Fernando on 16/10/2015.
 */
public class DiskDataSource implements DataSource {
    private Storage cache;

    public DiskDataSource(Storage cache) {
        this.cache = cache;
    }

    @Override
    public Observable<City> getForecast(final City city) {
        return Observable.just(city);
    }

    @Override
    public Observable<City> getCities() {
        return cache.getCities();
    }

    public Observable<City> saveCity(City city) {
        return cache.putCity(city);
    }

    public Observable<City> removeCity(City city) {
        return cache.removeCity(city);
    }
}
