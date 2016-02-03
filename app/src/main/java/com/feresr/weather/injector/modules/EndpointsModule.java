package com.feresr.weather.injector.modules;

import com.feresr.weather.ForecastIOEndpoints;
import com.feresr.weather.repository.Repository;
import com.feresr.weather.repository.RepositoryImp;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

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
