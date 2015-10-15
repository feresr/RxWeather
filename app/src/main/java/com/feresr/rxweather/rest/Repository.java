package com.feresr.rxweather.rest;

import com.feresr.rxweather.models.List;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Repository  {
    Observable<List> getForecast(String cityName);
}
