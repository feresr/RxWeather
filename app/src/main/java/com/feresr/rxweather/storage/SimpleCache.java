package com.feresr.rxweather.storage;

import android.os.SystemClock;

import com.feresr.rxweather.models.CityWeather;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Fernando on 16/10/2015.
 */
@Singleton
public class SimpleCache implements DataCache {

    private static final long EXPIRATION_TIME = 60 * 1000;

    private long lastUpdated = 0;
    private CityWeather cityWeather;

    @Inject
    public SimpleCache() {
    }

    @Override
    public boolean isExpired() {
        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME) ||
                cityWeather == null;
        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public rx.Observable<CityWeather> getForecast() {
        return Observable.create(new Observable.OnSubscribe<CityWeather>() {
            @Override
            public void call(Subscriber<? super CityWeather> subscriber) {
                subscriber.onNext(cityWeather);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public void evictAll() {
        cityWeather = null;
    }

    @Override
    public void putForecast(CityWeather cityWeather) {
        this.cityWeather = cityWeather;
        this.lastUpdated = SystemClock.uptimeMillis();
    }
}
