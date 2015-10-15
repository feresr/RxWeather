package com.feresr.rxweather.injector;

import com.feresr.rxweather.NetworkService;
import com.feresr.rxweather.UI.MainActivity;
import com.feresr.rxweather.injector.modules.AppModule;
import com.feresr.rxweather.injector.modules.EndpointsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Fernando on 13/10/2015.
 */

@Singleton @Component(modules = {EndpointsModule.class})
public interface WeatherApiComponent {
    void inject(MainActivity context);
    void inject(NetworkService context);
}
