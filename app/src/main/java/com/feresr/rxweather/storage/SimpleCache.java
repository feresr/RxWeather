package com.feresr.rxweather.storage;

import android.os.SystemClock;

import com.feresr.rxweather.models.Forecast;
import com.feresr.rxweather.models.Today;

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
    private Forecast forecast;
    private Today today;

    @Inject
    public SimpleCache() {
    }

    @Override
    public boolean isExpired() {
        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME) || forecast == null || today == null;
        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public rx.Observable<Forecast> getForecast() {
        return Observable.create(new Observable.OnSubscribe<Forecast>() {
            @Override
            public void call(Subscriber<? super Forecast> subscriber) {
                subscriber.onNext(forecast);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public Observable<Today> getTodaysWeather() {
        return Observable.create(new Observable.OnSubscribe<Today>() {
            @Override
            public void call(Subscriber<? super Today> subscriber) {
                subscriber.onNext(today);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void evictAll() {
        forecast = null;
        today = null;
    }

    @Override
    public void put(Forecast forecast) {
        this.forecast = forecast;
        this.lastUpdated = SystemClock.uptimeMillis();
    }

    @Override
    public void put(Today today) {
        this.today = today;
        this.lastUpdated = SystemClock.uptimeMillis();
    }
}
