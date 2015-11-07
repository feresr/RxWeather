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
public class SaveCityUseCase implements UseCase<City> {


    Repository repository;
    private String id;
    private String name;
    private Double lat;
    private Double lon;

    @Inject
    SaveCityUseCase(Repository repository) {
        this.repository = repository;
    }

    public void setParameters(String id, String name, Double lat, Double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public Observable<City> execute() {
        return repository.saveCity(id, name, lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
