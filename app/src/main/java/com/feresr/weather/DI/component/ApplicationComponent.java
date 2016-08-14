package com.feresr.weather.DI.component;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;

import com.feresr.weather.AlarmReceiver;
import com.feresr.weather.UI.fragment.CitiesFragment;
import com.feresr.weather.UI.fragment.ForecastFragment;
import com.feresr.weather.UI.fragment.SearchFragment;
import com.feresr.weather.DI.modules.DataBaseModule;
import com.feresr.weather.DI.modules.AppModule;
import com.feresr.weather.DI.modules.NetworkModule;
import com.feresr.weather.repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Fernando on 14/10/2015.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, DataBaseModule.class})
public interface ApplicationComponent {

    Repository getRepository();
    SharedPreferences getSharedPreferences();

    void inject(AlarmReceiver alarmReceiver);
}
