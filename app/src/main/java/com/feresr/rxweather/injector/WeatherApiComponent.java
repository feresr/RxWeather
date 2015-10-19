package com.feresr.rxweather.injector;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.feresr.rxweather.NetworkService;
import com.feresr.rxweather.UI.MainActivity;
import com.feresr.rxweather.injector.modules.ActivityModule;
import com.feresr.rxweather.injector.modules.AppModule;
import com.feresr.rxweather.injector.modules.EndpointsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Fernando on 13/10/2015.
 */

@ActivityScope @Component(modules = {EndpointsModule.class, ActivityModule.class}, dependencies = AppComponent.class)
public interface WeatherApiComponent {
    void inject(MainActivity activity);
}
