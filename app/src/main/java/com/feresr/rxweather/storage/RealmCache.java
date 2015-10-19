package com.feresr.rxweather.storage;

import android.content.Context;
import android.os.SystemClock;

import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.wrappers.RealmMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Fernando on 16/10/2015.
 */
@Singleton
public class RealmCache implements DataCache {

    private static final long EXPIRATION_TIME = 30 * 1000;

    private long lastUpdated = 0;
    private FiveDays fiveDays;

    @Inject
    public RealmCache() {
    }

    @Override
    public boolean isExpired() {
        if (lastUpdated == 0 || fiveDays == null) {
            return true;
        }

        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public rx.Observable<FiveDays> get() {
        return Observable.create(new Observable.OnSubscribe<FiveDays>() {
            @Override
            public void call(Subscriber<? super FiveDays> subscriber) {
                subscriber.onNext(fiveDays);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public void evictAll() {
        //TODO: clear cache
    }

    @Override
    public void put(FiveDays days) {
        this.fiveDays = days;
        lastUpdated = SystemClock.uptimeMillis();

    }
}
