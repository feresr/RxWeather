package com.feresr.weather.injector;

import com.feresr.weather.UI.CitiesFragment;
import com.feresr.weather.UI.ForecastFragment;
import com.feresr.weather.UI.SearchFragment;
import com.feresr.weather.injector.modules.ActivityModule;
import com.feresr.weather.injector.modules.EndpointsModule;

import dagger.Component;

/**
 * Created by Fernando on 13/10/2015.
 */

@ActivityScope
@Component(modules = {EndpointsModule.class, ActivityModule.class}, dependencies = AppComponent.class)
public interface WeatherApiComponent {
    void inject(ForecastFragment fragment);
    void inject(CitiesFragment fragment);
    void inject(SearchFragment fragment);
}
