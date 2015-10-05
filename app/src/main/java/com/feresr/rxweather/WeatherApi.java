package com.feresr.rxweather;

import com.feresr.rxweather.Models.Current;
import com.feresr.rxweather.Models.FiveDays;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Fernando on 5/10/2015.
 */
public class WeatherApi {
    private final WeatherEndpoints endpoints;

    public WeatherApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .baseUrl("http://api.openweathermap.org/data/2.5/weather")
                .build();

        endpoints = retrofit.create(WeatherEndpoints.class);
    }

    public Observable<Current> getCurrentWeather(String cityName) {
        return endpoints.getCurrent(cityName);
    }

    public Observable<com.feresr.rxweather.Models.List> getForecast(String cityName) {
        return endpoints.getForecast(cityName).flatMap(new Func1<FiveDays, Observable<com.feresr.rxweather.Models.List>>() {
            @Override
            public Observable<com.feresr.rxweather.Models.List> call(FiveDays fiveDays) {
                return Observable.from(fiveDays.getList());
            }
        });
    }
}
