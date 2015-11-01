package com.feresr.rxweather.domain;

import com.feresr.rxweather.models.Hour;
import com.feresr.rxweather.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 1/11/2015.
 * Get weather information for the following hours (current day)
 */
public class GetTodayForecastUseCase implements UseCase<Hour> {

    Repository repository;

    @Inject
    public GetTodayForecastUseCase(Repository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Observable<Hour> execute() {
        return repository.getTodayForecast("San Francisco, AR")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
