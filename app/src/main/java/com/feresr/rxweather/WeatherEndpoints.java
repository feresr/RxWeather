package com.feresr.rxweather;


import com.feresr.rxweather.models.DailyForecast;
import com.feresr.rxweather.models.HourlyForecast;
import com.feresr.rxweather.models.Today;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Fernando on 5/10/2015.
 */
public interface WeatherEndpoints {

    @GET("weather?")
    Observable<Today> getTodaysWeather(@Query("q") String city, @Query("units") String units, @Query("appid") String APIKEY);

    @GET("forecast/daily?")
    Observable<DailyForecast> getForecast(@Query("q") String city, @Query("cnt") int days, @Query("units") String units, @Query("appid") String APIKEY);

    //Weather data every 3 (three) hours
    @GET("forecast?")
    Observable<HourlyForecast> getTodaysForecast(@Query("q") String city, @Query("cnt") int cnt, @Query("units") String units, @Query("appid") String APIKEY);
}
