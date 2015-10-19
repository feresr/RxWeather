package com.feresr.rxweather;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.feresr.rxweather.domain.GetForecastUseCase;
import com.feresr.rxweather.models.Lista;

import javax.inject.Inject;

import rx.Subscriber;

public class NetworkService extends Service {

    @Inject
    GetForecastUseCase getForecastUseCase;

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

        getForecastUseCase.execute().cache().subscribe(new Subscriber<Lista>() {
            @Override
            public void onCompleted() {
                Log.e("LIST", "completed");
                stopSelf();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("LIST", e.toString());
            }

            @Override
            public void onNext(Lista lista) {
                ContentValues values = new ContentValues();
                values.put(WeatherProvider.temp, lista.getMain().getTemp());
                values.put(WeatherProvider.temp_max, lista.getMain().getTempMax());
                values.put(WeatherProvider.temp_min, lista.getMain().getTempMin());
                values.put(WeatherProvider.pressure, lista.getMain().getPressure());
                values.put(WeatherProvider.humidity, lista.getMain().getHumidity());
                values.put(WeatherProvider.weather_main, lista.getWeather().get(0).getMain());
                values.put(WeatherProvider.weather_desc, lista.getWeather().get(0).getDescription());
                values.put(WeatherProvider.weather_id, lista.getWeather().get(0).getId());


                getContentResolver().insert(WeatherProvider.CONTENT_URL, values);


                Log.e("LIST", lista.getWeather().get(0).getDescription());
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
