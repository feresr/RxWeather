package com.feresr.rxweather.repository;

import android.content.Context;

import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.storage.DataCache;

import rx.Observable;

/**
 * Created by Fernando on 16/10/2015.
 */
public class DiskDataSource implements DataSource {
    private DataCache cache;
    private Context context;

    public DiskDataSource(Context context, DataCache cache) {
        this.context = context;
        this.cache = cache;
    }

    @Override
    public Observable<FiveDays> getForecast(String city) {
        return cache.get();
    }
}
