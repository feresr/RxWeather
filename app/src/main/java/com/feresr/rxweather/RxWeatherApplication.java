package com.feresr.rxweather;

import android.app.Application;

import com.feresr.rxweather.injector.AppComponent;
import com.feresr.rxweather.injector.DaggerAppComponent;
import com.feresr.rxweather.injector.modules.AppModule;

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
