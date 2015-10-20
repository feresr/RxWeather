package com.feresr.rxweather.repository;

import com.feresr.rxweather.WeatherEndpoints;
import com.feresr.rxweather.models.Forecast;
import com.feresr.rxweather.models.Today;
import com.feresr.rxweather.storage.DataCache;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Fernando on 16/10/2015.
 */
public class CloudDataSource implements DataSource {

    static final String API_KEY = "84e96c23c2ca79ff736b21e3c66b88e8";
    private WeatherEndpoints endpoints;
    private DataCache cache;

    @Inject
    public CloudDataSource(WeatherEndpoints endpoints, DataCache cache) {
        this.endpoints = endpoints;
        this.cache = cache;
    }

    @Override
    public Observable<Forecast> getForecast(String city) {
        return endpoints.getForecast(city, 16, "metric", API_KEY).doOnNext(new Action1<Forecast>() {
            @Override
            public void call(Forecast forecast) {
                cache.put(forecast);
            }
        });
    }

    @Override
    public Observable<Today> getTodaysWeather(String city) {
        return endpoints.getTodaysWeather(city, "metric", API_KEY).doOnNext(new Action1<Today>() {
            @Override
            public void call(Today today) {
                cache.put(today);
            }
        });
    }
}
