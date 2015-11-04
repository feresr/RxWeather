package com.feresr.rxweather.injector.modules;

import com.feresr.rxweather.ForecastIOEndpoints;
import com.feresr.rxweather.repository.Repository;
import com.feresr.rxweather.repository.RepositoryImp;
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
    ForecastIOEndpoints provideEndpoints() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .baseUrl("https://api.forecast.io/")
                .build();
        return retrofit.create(ForecastIOEndpoints.class);
    }

    @Provides
    Repository provideDataRepository(RepositoryImp repositoryImp) {
        return repositoryImp;
    }
}
