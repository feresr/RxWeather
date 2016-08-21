package com.feresr.weather;

import android.app.Application;
import android.content.Context;

import com.feresr.weather.DI.component.ApplicationComponent;
import com.feresr.weather.DI.component.DaggerApplicationComponent;
import com.feresr.weather.DI.modules.AppModule;

/**
 * Created by Fernando on 14/10/2015.
 */
public class RxWeatherApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    public static RxWeatherApplication getApp(Context context) {
        return (RxWeatherApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder().appModule(new AppModule(this)).build();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
