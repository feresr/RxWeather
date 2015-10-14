package com.feresr.rxweather.injector.modules;

import com.feresr.rxweather.Models.City;
import com.feresr.rxweather.RestRepository;
import com.feresr.rxweather.WeatherEndpoints;
import com.feresr.rxweather.presenters.ForecastPresenter;
import com.feresr.rxweather.rest.Repository;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

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

    @Provides @Singleton
    City providesCity() {
        return new City();
    }

    @Provides
    Repository providesRepository(RestRepository repository) {return repository;}
}
