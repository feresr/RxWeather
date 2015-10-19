package com.feresr.rxweather.repository;


import com.feresr.rxweather.models.Forecast;
import com.feresr.rxweather.models.Today;
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
    public Observable<Forecast> getForecast(String city) {
        return cache.getForecast();
    }

    @Override
    public Observable<Today> getTodaysWeather(String city) {
        return cache.getTodaysWeather();
    }
}
