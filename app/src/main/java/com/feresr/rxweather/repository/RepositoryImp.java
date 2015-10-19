package com.feresr.rxweather.repository;

import com.feresr.rxweather.WeatherEndpoints;
import com.feresr.rxweather.models.Current;
import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Fernando on 5/10/2015.
 */
public class RepositoryImp implements Repository {
    private WeatherEndpoints endpoints;
    private DataStorageFactory dataStorageFactory;

    @Inject
    public RepositoryImp(DataStorageFactory factory) {
        this.dataStorageFactory = factory;
    }

    public Observable<Current> getCurrentWeather(String cityName, DataStorageFactory factory) {

        //return endpoints.getCurrent(cityName, API_KEY);
        return null;
    }

    public Observable<Lista> getForecast(String cityName) {
        final DataSource dataSource = this.dataStorageFactory.create();

        return dataSource.getForecast(cityName).flatMap(new Func1<FiveDays, Observable<Lista>>() {
            @Override
            public Observable<Lista> call(FiveDays fiveDays) {
                return Observable.from(fiveDays.getLista());
            }
        });
    }
}
