package com.feresr.rxweather.injector.modules;

import com.feresr.rxweather.RestRepository;
import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.rest.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fernando on 14/10/2015.
 */
@Module
public class AppModule {

    private final RxWeatherApplication rxWeatherApplication;

    public AppModule(RxWeatherApplication rxWeatherApplication) {

        this.rxWeatherApplication = rxWeatherApplication;
    }

    @Provides
    @Singleton
    RxWeatherApplication provideApplicationContext() {
        return rxWeatherApplication;
    }

    @Provides
    @Singleton
    Repository provideDataRepository(RestRepository restRepository) {
        return restRepository;
    }
}