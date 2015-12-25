package com.feresr.rxweather.repository;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;

import java.util.List;

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


    @Override
    public Observable<City> getForecast(City city) {
        final DataSource dataSource = this.dataStorageFactory.create(city.getId());
        return dataSource.getForecast(city);
    }

    @Override
    public Observable<List<City>> getCities() {
        DiskDataSource diskDataSource = dataStorageFactory.getDiskDataSource();
        return diskDataSource.getCities();
    }

    @Override
    public Observable<City> saveCity(City city) {
        DiskDataSource diskDataSource = dataStorageFactory.getDiskDataSource();
        return diskDataSource.saveCity(city);
    }

    @Override
    public Observable<City> removeCity(City city) {
        DiskDataSource diskDataSource = dataStorageFactory.getDiskDataSource();
        return diskDataSource.removeCity(city);
    }
}
