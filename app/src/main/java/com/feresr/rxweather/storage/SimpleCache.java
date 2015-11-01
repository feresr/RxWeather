package com.feresr.rxweather.storage;

import android.os.SystemClock;

import com.feresr.rxweather.models.DailyForecast;
import com.feresr.rxweather.models.HourlyForecast;
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
    private DailyForecast dailyForecast;
    private HourlyForecast hourlyForecast;
    private Today today;

    @Inject
    public SimpleCache() {
    }

    @Override
    public boolean isExpired() {
        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME) ||
                dailyForecast == null || today == null || hourlyForecast == null;
        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public rx.Observable<DailyForecast> getDailyForecast() {
        return Observable.create(new Observable.OnSubscribe<DailyForecast>() {
            @Override
            public void call(Subscriber<? super DailyForecast> subscriber) {
                subscriber.onNext(dailyForecast);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public Observable<HourlyForecast> getHourlyForecast() {
        return Observable.create(new Observable.OnSubscribe<HourlyForecast>() {
            @Override
            public void call(Subscriber<? super HourlyForecast> subscriber) {
                subscriber.onNext(hourlyForecast);
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
        dailyForecast = null;
        today = null;
        hourlyForecast = null;
    }

    @Override
    public void putNextDaysForecast(DailyForecast dailyForecast) {
        this.dailyForecast = dailyForecast;
        this.lastUpdated = SystemClock.uptimeMillis();
    }

    @Override
    public void putTodayWeather(Today today) {
        this.today = today;
        this.lastUpdated = SystemClock.uptimeMillis();
    }

    @Override
    public void putTodayForecast(HourlyForecast hours) {
        this.hourlyForecast = hours;
        this.lastUpdated = SystemClock.uptimeMillis();
    }
}
