package com.feresr.weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.feresr.weather.usecase.GetCitiesUseCase;
import com.feresr.weather.usecase.GetCityForecastUseCase;
import com.feresr.weather.models.City;
import com.feresr.weather.storage.SimpleCache;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Fernando on 19/3/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Inject
    GetCityForecastUseCase cityForecastUseCase;
    @Inject
    GetCitiesUseCase getCitiesUseCase;

    @Override
    public void onReceive(Context context, Intent intent) {
        ((RxWeatherApplication) context.getApplicationContext()).getComponent().inject(this);
        getCitiesUseCase.execute().flatMapIterable(new Func1<List<City>, Iterable<City>>() {
            @Override
            public Iterable<City> call(List<City> cities) {
                return cities;
            }
        }).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                cityForecastUseCase.setCity(city);
                cityForecastUseCase.setFetchIfExpired(true);
                return cityForecastUseCase.execute();
            }
        }).subscribe();


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                new Intent("com.feresr.weather.UPDATE_WEATHER_DATA"),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmUp) {
            Intent updateIntent = new Intent("com.feresr.weather.UPDATE_WEATHER_DATA");
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SimpleCache.REFRESH_TIME,
                    SimpleCache.REFRESH_TIME, pi);
        }
    }
}
