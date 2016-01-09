package com.feresr.weather.injector.modules;

import android.content.Context;

import com.feresr.weather.RxWeatherApplication;
import com.feresr.weather.storage.DataCache;
import com.feresr.weather.storage.SimpleCache;

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
    DataCache providesDataCache(SimpleCache cache) {
        return cache;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return rxWeatherApplication.getApplicationContext();
    }
}