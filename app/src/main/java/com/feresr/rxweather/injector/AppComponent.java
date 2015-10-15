package com.feresr.rxweather.injector;

import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.injector.modules.AppModule;
import com.feresr.rxweather.rest.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Fernando on 14/10/2015.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    RxWeatherApplication app();
}
