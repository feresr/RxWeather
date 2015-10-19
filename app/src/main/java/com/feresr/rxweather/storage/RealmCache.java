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
public class RealmCache implements DataCache {

    private static final long EXPIRATION_TIME = 60 * 1000;

    private long lastUpdated = 0;
    private Forecast fiveDays;

    @Inject
    public RealmCache() {
    }

    @Override
    public boolean isExpired() {
        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME) || fiveDays == null;

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
                subscriber.onNext(fiveDays);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public void evictAll() {
        fiveDays = null;
    }

    @Override
    public void put(Forecast days) {
        this.fiveDays = days;
        lastUpdated = SystemClock.uptimeMillis();
    }
}
