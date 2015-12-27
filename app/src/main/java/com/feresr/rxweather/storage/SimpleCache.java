package com.feresr.rxweather.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        return (System.currentTimeMillis() - weathers.get(cityId).getFetchTime() > EXPIRATION_TIME);
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
    public Observable<List<City>> getCities() {
        return Observable.create(new Observable.OnSubscribe<List<City>>() {
            @Override
            public void call(Subscriber<? super List<City>> subscriber) {
                ArrayList<City> cities = new ArrayList<>();

                try {
                    Cursor cursor = context.getContentResolver().query(WeatherContract.CityEntry.CONTENT_URI, null, null, null, null);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            City city = new City();
                            city.setId(cursor.getString(0));
                            city.setName(cursor.getString(1));
                            city.setLat(cursor.getDouble(2));
                            city.setLon(cursor.getDouble(3));
                            cities.add(city);
                        }
                        cursor.close();
                    }
                    subscriber.onNext(cities);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });

    }

    @Override
    public Observable<City> putCity(final City city) {
        return Observable.create(new Observable.OnSubscribe<City>() {
            @Override
            public void call(Subscriber<? super City> subscriber) {
                try {
                    ContentValues cityValues = new ContentValues();
                    cityValues.put(WeatherContract.CityEntry._ID, city.getId());
                    cityValues.put(WeatherContract.CityEntry.COLUMN_NAME_CITY_NAME, city.getName());
                    cityValues.put(WeatherContract.CityEntry.COLUMN_NAME_LAT, city.getLat());
                    cityValues.put(WeatherContract.CityEntry.COLUMN_NAME_LON, city.getLon());

                    context.getContentResolver().insert(WeatherContract.CityEntry.CONTENT_URI, cityValues);

                    subscriber.onNext(city);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<City> removeCity(final City city) {
        return Observable.create(new Observable.OnSubscribe<City>() {
            @Override
            public void call(Subscriber<? super City> subscriber) {
                try {
                    String[] params = {city.getId()};
                    context.getContentResolver().delete(WeatherContract.CityEntry.CONTENT_URI, WeatherContract.CityEntry._ID + " == ?", params);
                    subscriber.onNext(city);
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public void evictAll() {
        weathers.clear();
    }

    @Override
    public void putForecast(String cityId, CityWeather cityWeather) {
        cityWeather.setFetchTime(System.currentTimeMillis());
        weathers.put(cityId, cityWeather);
    }
}
