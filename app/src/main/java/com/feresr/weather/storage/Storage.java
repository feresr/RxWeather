package com.feresr.weather.storage;


import com.feresr.weather.models.City;
import com.feresr.weather.models.CityWeather;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface Storage {

    //CITIES

    /**
     * Stores a city into the internal storage
     *
     * @param city to be stored
     * @return an observable emitting the city that was just persisted
     */
    Observable<City> putCity(City city);

    /**
     * @return an observable emitting the entire collection of cities currently stored in the db
     */
    Observable<City> getCities();

    /**
     * Removes a specific city from the db
     *
     * @param city the city object to be removed
     * @return an observable emitting the city that was just removed
     */
    Observable<City> removeCity(City city);


    //FORECAST

    /**
     * Stores a {@link CityWeather} into the db
     *
     * @param cityId   of the city this weather data belongs to
     * @param forecast the weather data
     * @return an observable that emits the CityWeather that was just inserted
     */
    Observable<CityWeather> putForecast(String cityId, CityWeather forecast);

    Observable<CityWeather> getForecast(String cityId);

    Observable<CityWeather> removeForecast(CityWeather forecast);

    /**
     * Wipes out the db clean
     * Evict all elements of the db.
     */
    void evictAll();
}
