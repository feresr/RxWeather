package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Fernando on 5/10/2015.
 */
public class RepositoryImp implements Repository {

    private DataStorageFactory dataStorageFactory;

    @Inject
    public RepositoryImp(DataStorageFactory factory) {
        this.dataStorageFactory = factory;
    }


    public Observable<CityWeather> getForecast(String lat, String lon, String cityId) {
        final DataSource dataSource = this.dataStorageFactory.create(cityId);
        return dataSource.getForecast(cityId, lat, lon);
    }

    @Override
    public Observable<City> getCities() {
        DiskDataSource diskDataSource = dataStorageFactory.getDiskDataSource();
        return diskDataSource.getCities();
    }

    @Override
    public Observable<City> saveCity(String id, String name, Double lat, Double lon) {
        DiskDataSource diskDataSource = dataStorageFactory.getDiskDataSource();
        return diskDataSource.saveCity(id, name, lat, lon);
    }
}
