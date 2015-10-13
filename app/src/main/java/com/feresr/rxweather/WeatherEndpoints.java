package com.feresr.rxweather;

import com.feresr.rxweather.Models.Current;
import com.feresr.rxweather.Models.FiveDays;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Fernando on 5/10/2015.
 */
public interface WeatherEndpoints {
    @GET("weather?")
    Observable<Current> getCurrent(@Query("q") String city, @Query("appid") String APIKEY);

    @GET("forecast?")
    Observable<FiveDays> getForecast(@Query("q") String city, @Query("appid") String APIKEY);
}
