package com.feresr.rxweather.domain;

import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 14/10/2015.
 */
public class GetForecastUseCase implements UseCase<CityWeather> {

    private final Repository repository;

    private String lat;
    private String lon;

    @Inject
    GetForecastUseCase(Repository repository) {
        this.repository = repository;
    }

    public void setLatLon(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public Observable<CityWeather> execute() {
        return repository.getForecast(lat, lon)//("-31.4286", "-61.9143")
        .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
