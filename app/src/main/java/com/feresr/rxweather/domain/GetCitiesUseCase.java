package com.feresr.rxweather.domain;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 7/11/2015.
 */
public class GetCitiesUseCase implements UseCase<City> {
    Repository repository;

    @Inject
    GetCitiesUseCase(Repository repository) {
        this.repository = repository;
    }
    @Override
    public Observable<City> execute() {
        return repository.getCities().onBackpressureDrop()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
