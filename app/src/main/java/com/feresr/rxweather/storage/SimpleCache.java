package com.feresr.rxweather.storage;

import android.content.Context;
import android.os.SystemClock;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import java.util.HashMap;

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

    private static final long EXPIRATION_TIME = 30 * 60 * 1000;
    HashMap<String, CityWeather> weathers;
    private Context context;

    @Inject
    public SimpleCache(Context context) {
        this.context = context;
        weathers = new HashMap<>();
    }

    @Override
    public boolean isExpired(String cityId) {
        if (weathers.get(cityId) == null) {
            return true;
        }

        return (SystemClock.uptimeMillis() - weathers.get(cityId).getFetchTime() > EXPIRATION_TIME);
    }

    @Override
    public rx.Observable<CityWeather> getForecast(final String citiId) {
        return Observable.create(new Observable.OnSubscribe<CityWeather>() {
            @Override
            public void call(Subscriber<? super CityWeather> subscriber) {
                try {
                    subscriber.onNext(weathers.get(citiId));
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public Observable<City> getCities() {
        return Observable.create(new Observable.OnSubscribe<City>() {
            @Override
            public void call(Subscriber<? super City> subscriber) {
                try {
                    Realm realm = Realm.getInstance(context);
                    RealmResults<City> query = realm.where(City.class)
                            .findAll();
                    for (City city : query) {
                        subscriber.onNext(copyCity(city));
                    }
                    realm.close();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });

    }

    @Override
    public Observable<City> putCity(final String id, final String name, final Double lat, final Double lon) {
        return Observable.create(new Observable.OnSubscribe<City>() {
            @Override
            public void call(Subscriber<? super City> subscriber) {
                try {
                    Realm realm = Realm.getInstance(context);
                    realm.beginTransaction();
                    City city = realm.createObject(City.class);
                    city.setName(name);
                    city.setId(id);
                    city.setLat(lat);
                    city.setLon(lon);

                    City city1 = copyCity(city);

                    realm.commitTransaction();
                    realm.close();
                    subscriber.onNext(city1);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void evictAll() {
        weathers.clear();
    }

    @Override
    public void putForecast(String cityId, CityWeather cityWeather) {
        cityWeather.setFetchTime(SystemClock.uptimeMillis());
        weathers.put(cityId, cityWeather);
    }

    private City copyCity(City city) {
        City city1 = new City();
        city1.setName(city.getName());
        city1.setId(city.getId());
        city1.setLat(city.getLat());
        city1.setLon(city.getLon());
        return city1;
    }
}
