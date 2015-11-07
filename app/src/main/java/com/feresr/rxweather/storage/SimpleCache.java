package com.feresr.rxweather.storage;

import android.os.SystemClock;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Fernando on 16/10/2015.
 */
@Singleton
public class SimpleCache implements DataCache {

    private static final long EXPIRATION_TIME = 1000;//60 * 1000;

    private long lastUpdated = 0;
    private CityWeather cityWeather;
    private Realm realm;

    @Inject
    public SimpleCache(Realm realm) {
        this.realm = realm;
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
    public Observable<City> getCities() {
        RealmResults<City> query = realm.where(City.class)
                .findAll();
        ArrayList<City> cities = new ArrayList<>();
        for (City city :
                query) {
            cities.add(city);
        }
        return Observable.from(cities);
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
