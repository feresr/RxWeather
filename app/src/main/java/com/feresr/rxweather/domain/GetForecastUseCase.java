package com.feresr.rxweather.domain;

import com.feresr.rxweather.models.List;
import com.feresr.rxweather.rest.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 14/10/2015.
 */
public class GetForecastUseCase implements UseCase<List> {

    private final Repository repository;
    private final String cityName;

    @Inject
    GetForecastUseCase(Repository repository) {
        this.repository = repository;
        this.cityName = "Sydney";
    }

    @Override
    public Observable<List> execute() {
        return repository.getForecast(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
