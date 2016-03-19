package com.feresr.weather.injector;

import com.feresr.weather.AlarmReceiver;
import com.feresr.weather.UI.CitiesFragment;
import com.feresr.weather.UI.ForecastFragment;
import com.feresr.weather.UI.SearchFragment;
import com.feresr.weather.injector.modules.DataModule;
import com.feresr.weather.injector.modules.AppModule;
import com.feresr.weather.injector.modules.NetworkingModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Fernando on 14/10/2015.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkingModule.class, DataModule.class})
public interface AppComponent {
    void inject(ForecastFragment fragment);
    void inject(CitiesFragment fragment);
    void inject(SearchFragment fragment);
    void inject(AlarmReceiver alarmReceiver);
}
