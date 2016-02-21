package com.feresr.weather;

import android.app.Application;
import android.content.Context;

import com.feresr.weather.injector.AppComponent;
import com.feresr.weather.injector.DaggerAppComponent;
import com.feresr.weather.injector.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Fernando on 14/10/2015.
 */
public class RxWeatherApplication extends Application {

    private AppComponent mAppComponent;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        initializeInjector();
    }

    public static RefWatcher getRefWatcher(Context context) {
        RxWeatherApplication application = (RxWeatherApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private void initializeInjector() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
