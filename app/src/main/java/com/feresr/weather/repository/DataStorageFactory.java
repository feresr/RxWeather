package com.feresr.weather.repository;

import com.feresr.weather.models.City;
import com.feresr.weather.storage.DataCache;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Fernando on 16/10/2015.
 */
public class DataStorageFactory {

    private DataCache cache;
    private Lazy<DataSource> lazyDataSource;

    @Inject
    public DataStorageFactory(DataCache cache, Lazy<DataSource> lazyDataSource) {
        this.lazyDataSource = lazyDataSource;
        this.cache = cache;
    }

    public DataSource create(City city) {
        DataSource dataSource;
        if (this.cache.isExpired(city)) {
            dataSource = lazyDataSource.get();
        } else {
            dataSource = new DiskDataSource(this.cache);
        }
        return dataSource;
    }

    public DiskDataSource getDiskDataSource() {
        return new DiskDataSource(cache);
    }
}
