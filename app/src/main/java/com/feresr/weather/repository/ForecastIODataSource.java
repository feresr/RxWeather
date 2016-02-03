package com.feresr.weather.repository;

import android.content.Context;

import com.feresr.weather.ForecastIOEndpoints;
import com.feresr.weather.R;
import com.feresr.weather.models.City;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.storage.DataCache;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Fernando on 4/11/2015.
 */
public class ForecastIODataSource implements DataSource {

    private ForecastIOEndpoints endpoints;
    private DataCache cache;
    private Context context;

    @Inject
    public ForecastIODataSource(Context context, ForecastIOEndpoints endpoints, DataCache cache) {
        this.endpoints = endpoints;
        this.cache = cache;
        this.context = context;
    }


    @Override
    public Observable<City> getForecast(final City city) {
        String latlong = String.format("%s,%s", city.getLat(), city.getLon());
        return endpoints.getForecast(latlong, context.getResources().getString(R.string.forecastio_api_key), "ca", Locale.getDefault().getLanguage()).onErrorReturn(new Func1<Throwable, CityWeather>() {
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
