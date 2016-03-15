package com.feresr.weather.injector;

import android.content.Context;

import com.feresr.weather.RxWeatherApplication;
import com.feresr.weather.UI.CitiesFragment;
import com.feresr.weather.UI.ForecastFragment;
import com.feresr.weather.UI.SearchFragment;
import com.feresr.weather.injector.modules.DataModule;
import com.feresr.weather.injector.modules.AppModule;
import com.feresr.weather.injector.modules.NetworkingModule;
import com.feresr.weather.storage.DataCache;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Fernando on 14/10/2015.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkingModule.class, DataModule.class})
public interface AppComponent {

    RxWeatherApplication app();
    DataCache dataCache();
    Context context();

    void inject(ForecastFragment fragment);
    void inject(CitiesFragment fragment);
    void inject(SearchFragment fragment);
}
