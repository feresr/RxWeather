package com.feresr.rxweather.repository;

import android.content.Context;

import com.feresr.rxweather.storage.DataCache;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Fernando on 16/10/2015.
 */
public class DataStorageFactory {

    private DataCache cache;
    private Lazy<CloudDataSource> cloudDataSourceLazy;
    private Context context;

    @Inject
    public DataStorageFactory(Context context, DataCache cache, Lazy<CloudDataSource> cloudDataSourceLazy) {
        this.cloudDataSourceLazy = cloudDataSourceLazy;
        this.cache = cache;
        this.context = context;
    }

    public DataSource create() {
        DataSource dataSource;

        if (this.cache.isExpired()) {
            dataSource = cloudDataSourceLazy.get();
        } else {
            dataSource = new DiskDataSource(context, this.cache);
        }
        return dataSource;
    }
}
