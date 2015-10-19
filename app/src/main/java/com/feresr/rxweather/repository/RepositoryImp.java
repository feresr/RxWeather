package com.feresr.rxweather.repository;

import com.feresr.rxweather.WeatherEndpoints;
import com.feresr.rxweather.models.Day;

import com.feresr.rxweather.models.Forecast;


import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Fernando on 5/10/2015.
 */
public class RepositoryImp implements Repository {

    private DataStorageFactory dataStorageFactory;

    @Inject
    public RepositoryImp(DataStorageFactory factory) {
        this.dataStorageFactory = factory;
    }


    public Observable<Day> getForecast(String cityName) {
        final DataSource dataSource = this.dataStorageFactory.create();

        return dataSource.getForecast(cityName).flatMap(new Func1<Forecast, Observable<Day>>() {
            @Override
            public Observable<Day> call(Forecast forecast) {
                return Observable.from(forecast.getDays());
            }
        });
    }
}
