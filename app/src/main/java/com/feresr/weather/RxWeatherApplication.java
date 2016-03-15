package com.feresr.weather;

import android.app.Application;

import com.feresr.weather.injector.AppComponent;
import com.feresr.weather.injector.DaggerAppComponent;
import com.feresr.weather.injector.modules.AppModule;

/**
 * Created by Fernando on 14/10/2015.
 */
public class RxWeatherApplication extends Application {

    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
