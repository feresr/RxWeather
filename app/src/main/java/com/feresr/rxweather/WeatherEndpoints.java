package com.feresr.rxweather;


import com.feresr.rxweather.models.Forecast;
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
    Observable<Forecast> getForecast(@Query("q") String city, @Query("cnt") int days, @Query("units") String units, @Query("appid") String APIKEY);
}
