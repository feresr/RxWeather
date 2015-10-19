package com.feresr.rxweather.domain;

import com.feresr.rxweather.models.Today;
import com.feresr.rxweather.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 19/10/2015.
 */
public class GetTodaysWeatherUseCase implements UseCase<Today> {

    private final Repository repository;
    private final String city;

    @Inject
    public GetTodaysWeatherUseCase(Repository repository) {
        super();
        this.repository = repository;
        this.city = "Sydney";
    }

    @Override
    public Observable<Today> execute() {
        return repository.getTodaysWeather(city)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
