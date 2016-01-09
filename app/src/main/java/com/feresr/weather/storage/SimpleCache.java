package com.feresr.weather.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.feresr.weather.models.City;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.models.Currently;
import com.feresr.weather.models.Daily;
import com.feresr.weather.models.Day;
import com.feresr.weather.models.Hour;
import com.feresr.weather.models.Hourly;

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

    private static final long EXPIRATION_TIME = 20 * 60 * 1000;
    private Context context;

    @Inject
    public SimpleCache(Context context) {
        this.context = context;
    }

    @Override
    public boolean isExpired(City city) {
        if (city.getCityWeather() == null) {
            return true;
        }

        return (System.currentTimeMillis() - city.getCityWeather().getFetchTime() > EXPIRATION_TIME);
    }

    //Not in use, we fetch the forecast from the db when querying from the city
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

                Daily daily = new Daily();
                daily.setSummary(cursor.getString(19));
                daily.setIcon(cursor.getString(20));
                cityWeather.setDaily(daily);

                Hourly hourly = new Hourly();
                hourly.setSummary(cursor.getString(21));
                hourly.setIcon(cursor.getString(22));
                cityWeather.setHourly(hourly);

                cityWeather.setCurrently(currently);

            }
            cursor.close();
        }

        if (cityWeather != null) {
            cityWeather.getHourly().setData(getHourlyFromCityId(cityId));
            cityWeather.getDaily().setDays(getDaysFromCityId(cityId));
        }

        return cityWeather;
    }

    private List<Day> getDaysFromCityId(String cityId) {
        ArrayList<Day> days = null;
        String[] params = {cityId};
        Cursor cursor = context.getContentResolver().query(WeatherContract.DayEntry.CONTENT_URI, null, WeatherContract.DayEntry.CITY_ID + " = ?", params, null);

        if (cursor != null) {
            try {
                days = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Day day = new Day();
                    //0 is id
                    day.setTime(cursor.getInt(1));
                    day.setSummary(cursor.getString(2));
                    day.setIcon(cursor.getString(3));
                    day.setSunriseTime(cursor.getInt(4));
                    day.setSunsetTime(cursor.getInt(5));
                    day.setMoonPhase(cursor.getDouble(6));
                    day.setPrecipIntensity(cursor.getDouble(7));
                    day.setPrecipIntensityMax(cursor.getDouble(8));
                    day.setPrecipIntensityMaxTime(cursor.getDouble(9));
                    day.setPrecipProbability(cursor.getDouble(10));
                    day.setPrecipType(cursor.getString(11));
                    day.setTemperatureMax(cursor.getDouble(12));
                    day.setTemperatureMaxTime(cursor.getInt(13));
                    day.setTemperatureMin(cursor.getDouble(14));
                    day.setTemperatureMinTime(cursor.getInt(15));
                    day.setApparentTemperatureMax(cursor.getDouble(16));
                    day.setApparentTemperatureMaxTime(cursor.getInt(17));
                    day.setApparentTemperatureMin(cursor.getDouble(18));
                    day.setApparentTemperatureMinTime(cursor.getInt(19));
                    day.setDewPoint(cursor.getDouble(20));
                    day.setHumidity(cursor.getDouble(21));
                    day.setWindSpeed(cursor.getDouble(22));
                    day.setWindBearing(cursor.getInt(23));
                    day.setCloudCover(cursor.getDouble(24));
                    day.setPressure(cursor.getDouble(25));
                    day.setOzone(cursor.getDouble(26));
                    days.add(day);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }

        }
        return days;
    }

    private List<Hour> getHourlyFromCityId(String cityId) {
        ArrayList<Hour> hours = null;
        String[] params = {cityId};
        Cursor cursor = context.getContentResolver().query(WeatherContract.HourEntry.CONTENT_URI, null, WeatherContract.HourEntry.CITY_ID + " = ?", params, null);

        if (cursor != null) {
            try {
                hours = new ArrayList<>();
                while (cursor.moveToNext()) {
                    Hour hour = new Hour();
                    //0 is id
                    hour.setTime(cursor.getInt(1));
                    hour.setSummary(cursor.getString(2));
                    hour.setIcon(cursor.getString(3));
                    hour.setPrecipIntensity(cursor.getDouble(4));
                    hour.setPrecipProbability(cursor.getDouble(5));
                    hour.setPrecipType(cursor.getString(6));
                    hour.setTemperature(cursor.getDouble(7));
                    hour.setApparentTemperature(cursor.getDouble(8));
                    hour.setDewPoint(cursor.getDouble(9));
                    hour.setHumidity(cursor.getDouble(10));
                    hour.setWindSpeed(cursor.getDouble(11));
                    hour.setWindBearing(cursor.getInt(12));
                    hour.setCloudCover(cursor.getDouble(13));
                    hour.setPressure(cursor.getDouble(14));
                    hour.setOzone(cursor.getDouble(15));
                    hours.add(hour);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }

        }
        return hours;
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

                            city.setCityWeather(getCityWeatherFromCityId(city.getId()));
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
                    context.getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI, WeatherContract.WeatherEntry._ID + " = ?", params);
                    context.getContentResolver().delete(WeatherContract.HourEntry.CONTENT_URI, WeatherContract.HourEntry.CITY_ID + " = ?", params);
                    context.getContentResolver().delete(WeatherContract.DayEntry.CONTENT_URI, WeatherContract.DayEntry.CITY_ID + " = ?", params);
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
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DAILY_SUMMARY, cityWeather.getDaily().getSummary());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DAILY_ICON, cityWeather.getDaily().getIcon());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HOURLY_SUMMARY, cityWeather.getHourly().getSummary());
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HOURLY_ICON, cityWeather.getHourly().getIcon());

        ContentValues[] hourlyValues = new ContentValues[cityWeather.getHourly().getData().size()];
        int i = 0;
        for (Hour hour: cityWeather.getHourly().getData()) {
            hourlyValues[i] = new ContentValues();
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_TIME, hour.getTime());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_SUMMARY, hour.getSummary());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_ICON, hour.getIcon());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_PRECIP_INTENSITY, hour.getPrecipIntensity());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_PRECIP_PROBABILITY, hour.getPrecipProbability());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_PRECIP_TYPE, hour.getPrecipProbability());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_TEMP, hour.getTemperature());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_APPARENT_TEMP, hour.getApparentTemperature());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_DEW_POINT, hour.getDewPoint());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_HUMIDITY, hour.getHumidity());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_WIND_SPEED, hour.getWindSpeed());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_WIND_BEARING, hour.getWindBearing());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_CLOUD_COVER, hour.getCloudCover());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_PRESSURE, hour.getPressure());
            hourlyValues[i].put(WeatherContract.HourEntry.COLUMN_OZONE, hour.getOzone());
            hourlyValues[i].put(WeatherContract.HourEntry.CITY_ID, cityId);
            i++;
        }

        ContentValues[] dailyValues = new ContentValues[cityWeather.getDaily().getDays().size()];
        i = 0;
        for (Day day : cityWeather.getDaily().getDays()) {
            dailyValues[i] = new ContentValues();
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_TIME, day.getTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_SUMMARY, day.getSummary());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_ICON, day.getIcon());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_SUNRISE_TIME, day.getSunriseTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_SUNSET_TIME, day.getSunsetTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_MOON_PHASE, day.getMoonPhase());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_PRECIP_INTENSITY, day.getPrecipIntensity());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_PRECIP_INTENSITY_MAX, day.getPrecipIntensityMax());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_PRECIP_INTENSITY_MAX_TIME, day.getPrecipIntensityMaxTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_PRECIP_PROBABILITY, day.getPrecipProbability());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_PRECIP_TYPE, day.getPrecipProbability());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_TEMPERATURE_MAX, day.getTemperatureMax());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_TEMPERATURE_MAX_TIME, day.getTemperatureMaxTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_TEMPERATURE_MIN, day.getTemperatureMin());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_TEMPERATURE_MIN_TIME, day.getTemperatureMinTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MAX, day.getApparentTemperatureMax());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MAX_TIME, day.getApparentTemperatureMaxTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MIN, day.getApparentTemperatureMin());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MIN_TIME, day.getApparentTemperatureMinTime());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_DEW_POINT, day.getDewPoint());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_HUMIDITY, day.getHumidity());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_WIND_SPEED, day.getWindSpeed());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_WIND_BEARING, day.getWindBearing());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_CLOUD_COVER, day.getCloudCover());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_PRESSURE, day.getPressure());
            dailyValues[i].put(WeatherContract.DayEntry.COLUMN_OZONE, day.getOzone());
            dailyValues[i].put(WeatherContract.DayEntry.CITY_ID, cityId);
            i++;
        }


        context.getContentResolver().insert(WeatherContract.WeatherEntry.CONTENT_URI, weatherValues);

        //Remove previous hourly and daily data
        String[] params = {cityId};
        context.getContentResolver().delete(WeatherContract.HourEntry.CONTENT_URI, WeatherContract.HourEntry.CITY_ID + " = ?", params);
        context.getContentResolver().delete(WeatherContract.DayEntry.CONTENT_URI, WeatherContract.DayEntry.CITY_ID + " = ?", params);

        context.getContentResolver().bulkInsert(WeatherContract.HourEntry.CONTENT_URI, hourlyValues);
        context.getContentResolver().bulkInsert(WeatherContract.DayEntry.CONTENT_URI, dailyValues);

    }
}
