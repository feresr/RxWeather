package com.feresr.rxweather.repository;

import android.content.Context;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.models.Main;
import com.feresr.rxweather.models.Weather;
import com.feresr.rxweather.models.wrappers.FiveDaysWrapper;
import com.feresr.rxweather.storage.DataCache;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

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
