package com.feresr.weather.usecase;

import com.feresr.weather.models.City;
import com.feresr.weather.storage.Storage;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fernando on 7/11/2015.
 * Retrieves the list of Cities as an observable from the injected local storage mechanism
 */
public class GetCitiesUseCase extends UseCase<City> {

    private Storage storage;

    @Inject
    GetCitiesUseCase(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Observable<City> execute() {
        return storage.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
