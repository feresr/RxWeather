package com.feresr.rxweather.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.models.Currently;

import java.util.ArrayList;
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
    private Context context;

    @Inject
    public SimpleCache(Context context) {
        this.context = context;
    }

    @Override
    public boolean isExpired(City city) {
        CityWeather cityWeather = getCityWeatherFromCityId(city.getId());
        if (cityWeather == null) {
            return true;
        }

        return (System.currentTimeMillis() - cityWeather.getFetchTime() > EXPIRATION_TIME);
    }

    @Override
    public rx.Observable<CityWeather> getForecast(final String cityId) {
        return Observable.create(new Observable.OnSubscribe<CityWeather>() {
            @Override
            public void call(Subscriber<? super CityWeather> subscriber) {
                try {
                    subscriber.onNext(getCityWeatherFromCityId(cityId));
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    private CityWeather getCityWeatherFromCityId(String cityId) {
        String[] params = {cityId};
        CityWeather cityWeather = null;

        Cursor cursor = context.getContentResolver().query(WeatherContract.WeatherEntry.CONTENT_URI, null, WeatherContract.WeatherEntry._ID + " = ?", params, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                cityWeather = new CityWeather();
                //0 is id
                cityWeather.setTimezone(cursor.getString(1));
                cityWeather.setOffset(cursor.getDouble(2));
                cityWeather.setFetchTime(cursor.getLong(3));

                Currently currently = new Currently();
                currently.setTime(cursor.getInt(4));
                currently.setSummary(cursor.getString(5));
                currently.setIcon(cursor.getString(6));
                currently.setPrecipIntensity(cursor.getDouble(7));
                currently.setPrecipProbability(cursor.getDouble(8));
                currently.setPrecipType(cursor.getString(9));
                currently.setTemperature(cursor.getDouble(10));
                currently.setApparentTemperature(cursor.getDouble(11));
                currently.setDewPoint(cursor.getDouble(12));
                currently.setHumidity(cursor.getDouble(13));
                currently.setWindSpeed(cursor.getDouble(14));
                currently.setWindBearing(cursor.getDouble(15));
                currently.setCloudCover(cursor.getDouble(16));
                currently.setPressure(cursor.getDouble(17));
                currently.setOzone(cursor.getDouble(18));

                cityWeather.setCurrently(currently);
            }
            cursor.close();
        }

        return cityWeather;
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
                    context.getContentResolver().delete(WeatherContract.CityEntry.CONTENT_URI, WeatherContract.CityEntry._ID + " = ?", params);
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
        //weathers.clear();
    }

    @Override
    public void putForecast(String cityId, CityWeather cityWeather) {

        ContentValues weatherValues = new ContentValues();
        weatherValues.put(WeatherContract.WeatherEntry._ID, cityId);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_TIME_ZONE, cityWeather.getTimezone());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_OFFSET, cityWeather.getOffset());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_FETCH_TIME, System.currentTimeMillis());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_TIME, cityWeather.getCurrently().getTime());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_SUMMARY, cityWeather.getCurrently().getSummary());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_ICON, cityWeather.getCurrently().getIcon());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRECIP_INTENSITY, cityWeather.getCurrently().getPrecipIntensity());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRECIP_PROBABILITY, cityWeather.getCurrently().getPrecipProbability());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRECIP_TYPE, cityWeather.getCurrently().getPrecipType());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_TEMP, cityWeather.getCurrently().getTemperature());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_APPARENT_TEMP, cityWeather.getCurrently().getApparentTemperature());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEW_POINT, cityWeather.getCurrently().getDewPoint());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, cityWeather.getCurrently().getHumidity());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, cityWeather.getCurrently().getWindSpeed());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_BEARING, cityWeather.getCurrently().getWindBearing());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_CLOUD_COVER, cityWeather.getCurrently().getCloudCover());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, cityWeather.getCurrently().getPressure());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_OZONE, cityWeather.getCurrently().getOzone());

        context.getContentResolver().insert(WeatherContract.WeatherEntry.CONTENT_URI, weatherValues);

    }
}
