package com.feresr.rxweather.DI;

import com.feresr.rxweather.WeatherEndpoints;
import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Fernando on 13/10/2015.
 */
@Module
public class EndpointsModule {

    @Provides
    WeatherEndpoints provideEndpoints() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .build();
        return retrofit.create(WeatherEndpoints.class);
    }
}
