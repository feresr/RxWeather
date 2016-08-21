package com.feresr.weather.DI.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.feresr.weather.R;
import com.feresr.weather.repository.DataSource;
import com.feresr.weather.repository.ForecastIODataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fernando on 14/10/2015.
 */
@Module
public class DataBaseModule {

    public DataBaseModule() {

    }

    @Provides
    @Singleton
    DataSource providesDataSource(ForecastIODataSource dataSource) {
        return dataSource;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }
}
