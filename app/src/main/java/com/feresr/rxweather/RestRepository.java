package com.feresr.rxweather;

import com.feresr.rxweather.Models.Current;
import com.feresr.rxweather.Models.FiveDays;
import com.feresr.rxweather.rest.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Fernando on 5/10/2015.
 */
public class RestRepository implements Repository {
    private WeatherEndpoints endpoints;
    static final String API_KEY = "84e96c23c2ca79ff736b21e3c66b88e8";

    @Inject
    public RestRepository(WeatherEndpoints endpoints) {
        this.endpoints = endpoints;
    }

    public Observable<Current> getCurrentWeather(String cityName) {
        return endpoints.getCurrent(cityName, API_KEY);
    }

    public Observable<com.feresr.rxweather.Models.List> getForecast(String cityName) {
        return endpoints.getForecast(cityName, API_KEY).flatMap(new Func1<FiveDays, Observable<com.feresr.rxweather.Models.List>>() {
            @Override
            public Observable<com.feresr.rxweather.Models.List> call(FiveDays fiveDays) {
                return Observable.from(fiveDays.getList());
            }
        });
    }
}
