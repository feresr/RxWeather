package com.feresr.weather.usecase;

import com.feresr.weather.models.City;
import com.feresr.weather.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 7/11/2015.
 */
public class RemoveCityUseCase implements UseCase<City> {


    Repository repository;
    private City city;

    @Inject
    RemoveCityUseCase(Repository repository) {
        this.repository = repository;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public Observable<City> execute() {
        return repository.removeCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
