package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.FiveDays;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataSource {
    Observable<FiveDays> getForecast(String city);
}
