package com.feresr.rxweather.storage;

import android.os.SystemClock;

import com.feresr.rxweather.models.Forecast;

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

    @Inject
    public SimpleCache() {
    }

    @Override
    public boolean isExpired() {
        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME) || forecast == null;
        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public rx.Observable<Forecast> get() {
        return Observable.create(new Observable.OnSubscribe<Forecast>() {
            @Override
            public void call(Subscriber<? super Forecast> subscriber) {
                subscriber.onNext(forecast);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public void evictAll() {
        forecast = null;
    }

    @Override
    public void put(Forecast forecast) {
        this.forecast = forecast;
        this.lastUpdated = SystemClock.uptimeMillis();
    }
}
