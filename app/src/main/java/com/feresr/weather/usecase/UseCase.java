package com.feresr.weather.usecase;

import rx.Observable;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface UseCase<T> {
    Observable<T> execute();
}
