package com.feresr.rxweather.repository;

import com.feresr.rxweather.ForecastIOEndpoints;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.storage.DataCache;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

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
    public Observable<City> getForecast(final City city) {
        String latlong = String.format("%s,%s", city.getLat(), city.getLon());
        return endpoints.getForecast(latlong, API_KEY, "ca").onErrorReturn(new Func1<Throwable, CityWeather>() {
            @Override
            public CityWeather call(Throwable throwable) {
                return null;
            }
        }).map(new Func1<CityWeather, City>() {
            @Override
            public City call(CityWeather cityWeather) {
                if (cityWeather != null) {
                    cache.putForecast(city.getId(), cityWeather);
                    cityWeather.setFetchTime(System.currentTimeMillis());
                    city.setCityWeather(cityWeather);
                }
                return city;
            }
        });
    }

    @Override
    public Observable<List<City>> getCities() {
        //We do not get cities from ForecastIO
        return null;
    }
}
