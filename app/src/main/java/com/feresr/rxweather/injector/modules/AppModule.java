package com.feresr.rxweather.injector.modules;

import android.content.Context;

import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.storage.DataCache;
import com.feresr.rxweather.storage.SimpleCache;

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