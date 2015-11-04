package com.feresr.rxweather.injector.modules;

import android.content.Context;

import com.feresr.rxweather.repository.DataSource;
import com.feresr.rxweather.repository.ForecastIODataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fernando on 14/10/2015.
 */
@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context context) {
        this.mContext = context;
    }

    @Provides
    DataSource providesDataSource(ForecastIODataSource dataSource) {
        return dataSource;
    }
}
