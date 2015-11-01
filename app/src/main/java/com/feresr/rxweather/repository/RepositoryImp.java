package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.Day;

import com.feresr.rxweather.models.DailyForecast;
import com.feresr.rxweather.models.Hour;
import com.feresr.rxweather.models.HourlyForecast;
import com.feresr.rxweather.models.Today;


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

    public Observable<Today> getTodaysWeather(String cityName) {
        final DataSource dataSource = this.dataStorageFactory.create();

        return dataSource.getTodaysWeather(cityName);
    }

    public Observable<Day> getForecast(String cityName) {
        final DataSource dataSource = this.dataStorageFactory.create();

        return dataSource.getForecast(cityName).flatMap(new Func1<DailyForecast, Observable<Day>>() {
            @Override
            public Observable<Day> call(DailyForecast dailyForecast) {
                return Observable.from(dailyForecast.getDays());
            }
        });
    }

    @Override
    public Observable<Hour> getTodayForecast(String cityName) {
        final DataSource dataSource = this.dataStorageFactory.create();

        return dataSource.getTodaysForecast(cityName).flatMap(new Func1<HourlyForecast, Observable<Hour>>() {
            @Override
            public Observable<Hour> call(HourlyForecast hourlyForecast) {
                return Observable.from(hourlyForecast.getDays());
            }
        });
    }
}
