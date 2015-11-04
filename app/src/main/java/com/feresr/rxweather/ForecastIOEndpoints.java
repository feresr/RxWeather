package com.feresr.rxweather;

import com.feresr.rxweather.models.CityWeather;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Fernando on 5/10/2015.
 */
public interface ForecastIOEndpoints {
    //Example call https://api.forecast.io/forecast/3021a64fa44fe78d95b05991be3fecc4/-31.4286,-61.9143
    @GET("forecast/{apiKey}/{latLon}")
    Observable<CityWeather> getForecast(@Path(value="latLon", encoded=true) String latLong, @Path("apiKey") String APIKEY, @Query("units") String units);
}
