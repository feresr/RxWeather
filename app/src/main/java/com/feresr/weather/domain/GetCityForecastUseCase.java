package com.feresr.weather.domain;

import com.feresr.weather.models.City;
import com.feresr.weather.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 14/10/2015.
 */
public class GetCityForecastUseCase implements UseCase<City> {

    private final Repository repository;
    private boolean fetchIfExpired = false;

    private City city;

    @Inject
    GetCityForecastUseCase(Repository repository) {
        this.repository = repository;
    }

    public GetCityForecastUseCase setCity(City city) {
        this.city = city;
        return this;
    }

    public GetCityForecastUseCase setFetchIfExpired(boolean fetchIfExpired) {
        this.fetchIfExpired = fetchIfExpired;
        return this;
    }

    @Override
    public Observable<City> execute() {
        return repository.getForecast(city, fetchIfExpired)//("-31.4286", "-61.9143")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
