package com.feresr.rxweather.injector;

import com.feresr.rxweather.UI.ForecastFragment;
import com.feresr.rxweather.injector.modules.ActivityModule;
import com.feresr.rxweather.injector.modules.EndpointsModule;

import dagger.Component;

/**
 * Created by Fernando on 13/10/2015.
 */

@ActivityScope
@Component(modules = {EndpointsModule.class, ActivityModule.class}, dependencies = AppComponent.class)
public interface WeatherApiComponent {
    void inject(ForecastFragment activity);
}
