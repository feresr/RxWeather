package com.feresr.rxweather.injector.modules;

import com.feresr.rxweather.repository.DataStorageFactory;
import com.feresr.rxweather.repository.RepositoryImp;
import com.feresr.rxweather.WeatherEndpoints;
import com.feresr.rxweather.presenters.ForecastPresenter;
import com.feresr.rxweather.presenters.Presenter;
import com.feresr.rxweather.repository.Repository;
import com.feresr.rxweather.storage.DataCache;
import com.feresr.rxweather.storage.RealmCache;
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
@Module @Singleton
public class EndpointsModule {

    @Provides @Singleton
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
    Presenter providesPresenter(ForecastPresenter presenter) {
        return presenter;
    }


    @Provides
    @Singleton
    Repository provideDataRepository(RepositoryImp repositoryImp) {
        return repositoryImp;
    }

    @Provides
    @Singleton
    DataCache providesDataCache(RealmCache cache) {
        return cache;
    }
}
