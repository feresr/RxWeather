package com.feresr.weather;

import com.feresr.weather.models.CityWeather;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Fernando on 5/10/2015.
 */
public interface ForecastIOEndpoints {
    //Example call https://api.forecast.io/forecast/3021a64fa44fe78d95b05991be3fecc4/-31.4286,-61.9143
    @GET("forecast/{apiKey}/{latLon}")
    Observable<CityWeather> getForecast(@Path(value="latLon", encoded=true) String latLong, @Path("apiKey") String APIKEY, @Query("units") String units, @Query("lang") String lang);
}
