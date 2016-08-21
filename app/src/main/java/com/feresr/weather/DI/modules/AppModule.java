package com.feresr.weather.DI.modules;

import android.content.Context;

import com.feresr.weather.RxWeatherApplication;
import com.feresr.weather.storage.SQLiteStorage;
import com.feresr.weather.storage.Storage;

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
    Storage providesDataCache(SQLiteStorage cache) {
        return cache;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return rxWeatherApplication.getApplicationContext();
    }
}