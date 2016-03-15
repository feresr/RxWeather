package com.feresr.weather.injector.modules;

import com.feresr.weather.repository.DataSource;
import com.feresr.weather.repository.ForecastIODataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fernando on 14/10/2015.
 */
@Module
public class DataModule {

    public DataModule() {

    }

    @Provides @Singleton
    DataSource providesDataSource(ForecastIODataSource dataSource) {
        return dataSource;
    }
}
