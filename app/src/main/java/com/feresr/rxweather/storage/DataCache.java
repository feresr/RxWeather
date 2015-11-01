package com.feresr.rxweather.storage;



import com.feresr.rxweather.models.DailyForecast;
import com.feresr.rxweather.models.HourlyForecast;
import com.feresr.rxweather.models.Today;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public interface DataCache {
    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();

    void putNextDaysForecast(DailyForecast days);
    void putTodayWeather(Today today);
    void putTodayForecast(HourlyForecast hours);

    Observable<DailyForecast> getDailyForecast();

    Observable<HourlyForecast> getHourlyForecast();

    Observable<Today> getTodaysWeather();
}
