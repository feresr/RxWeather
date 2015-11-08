package com.feresr.rxweather.repository;

import com.feresr.rxweather.storage.DataCache;

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

    public DataSource create(String cityId) {
        DataSource dataSource;
        if (this.cache.isExpired(cityId)) {
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
