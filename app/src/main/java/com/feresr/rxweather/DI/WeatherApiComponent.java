package com.feresr.rxweather.DI;

import com.feresr.rxweather.NetworkService;
import com.feresr.rxweather.WeatherApi;

import dagger.Component;

/**
 * Created by Fernando on 13/10/2015.
 */
@Component(modules = EndpointsModule.class)
public interface WeatherApiComponent {
    WeatherApi provideWeatherApi();
    void inject(NetworkService context);
}
