package com.feresr.rxweather.repository;

import com.feresr.rxweather.ForecastIOEndpoints;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.storage.DataCache;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Fernando on 4/11/2015.
 */
public class ForecastIODataSource implements DataSource {

    static final String API_KEY = "3021a64fa44fe78d95b05991be3fecc4";
    private ForecastIOEndpoints endpoints;
    private DataCache cache;

    @Inject
    public ForecastIODataSource(ForecastIOEndpoints endpoints, DataCache cache) {
        this.endpoints = endpoints;
        this.cache = cache;
    }

    @Override
    public Observable<CityWeather> getForecast(final String cityId, String lat, String lon) {
        String latlong = String.format("%s,%s", lat, lon);
        return endpoints.getForecast(latlong, API_KEY, "si").doOnNext(new Action1<CityWeather>() {
            @Override
            public void call(CityWeather cityWeather) {
                cache.putForecast(cityId, cityWeather);
            }
        });
    }

    @Override
    public Observable<City> getCities() {
        //We do not get cities from ForecastIO
        return null;
    }
}
