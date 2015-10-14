package com.feresr.rxweather;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.feresr.rxweather.Models.List;

import javax.inject.Inject;

import rx.Subscriber;

public class NetworkService extends Service {

    @Inject
    RestRepository restRepository;

    public NetworkService() {
        //DaggerWeatherApiComponent.builder().build().inject(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("weather", "Service started");
        /*restRepository.getCurrentWeather("Sydney").subscribe(new Subscriber<Current>() {
            @Override
            public void onCompleted() {
                stopSelf();
                Log.e("weather", "Service stopped");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("weather", "Service stopped");
                stopSelf();
            }

            @Override
            public void onNext(Current current) {

                ContentValues values = new ContentValues();
                values.put(WeatherProvider.name, "Sydney");
                values.put(WeatherProvider.weather, current.getWeather().get(0).getDescription());

                // Provides access to other applications Content Providers
                Uri uri = getContentResolver().insert(WeatherProvider.CONTENT_URL, values);


                Log.e("weather", current.getWeather().get(0).getDescription());
            }
        });*/
        getContentResolver().delete(WeatherProvider.CONTENT_URL, null, null);

        restRepository.getForecast("Sydney").subscribe(new Subscriber<List>() {
            @Override
            public void onCompleted() {
                Log.e("LIST", "completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("LIST", e.toString());
            }

            @Override
            public void onNext(List list) {
                ContentValues values = new ContentValues();
                values.put(WeatherProvider.temp, list.getMain().getTemp());
                values.put(WeatherProvider.temp_max, list.getMain().getTempMax());
                values.put(WeatherProvider.temp_min, list.getMain().getTempMin());
                values.put(WeatherProvider.pressure, list.getMain().getPressure());
                values.put(WeatherProvider.humidity, list.getMain().getHumidity());
                values.put(WeatherProvider.weather_main, list.getWeather().get(0).getMain());
                values.put(WeatherProvider.weather_desc, list.getWeather().get(0).getDescription());
                values.put(WeatherProvider.weather_id, list.getWeather().get(0).getId());


                getContentResolver().insert(WeatherProvider.CONTENT_URL, values);


                Log.e("LIST", list.getWeather().get(0).getDescription());
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
