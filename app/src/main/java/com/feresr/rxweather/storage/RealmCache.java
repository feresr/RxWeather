package com.feresr.rxweather.storage;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.models.Weather;
import com.feresr.rxweather.models.wrappers.FiveDaysWrapper;
import com.feresr.rxweather.models.wrappers.TimeStampWrapper;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Fernando on 16/10/2015.
 */
@Singleton
public class RealmCache implements DataCache {

    private static final long EXPIRATION_TIME =  30 * 1000;

    private Context context;

    @Inject
    public RealmCache(Context context) {
        this.context = context;
    }

    @Override
    public boolean isExpired() {

        Realm realm = Realm.getInstance(context);

        TimeStampWrapper lastUpdatedTimeWrapper = realm.where(TimeStampWrapper.class).findFirst();
        if (lastUpdatedTimeWrapper == null) {
            return true;
        }


        boolean expired = (SystemClock.uptimeMillis() - lastUpdatedTimeWrapper.getLastime() > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public rx.Observable<FiveDays> get() {
        Realm realm = Realm.getInstance(context);
        RealmResults<FiveDaysWrapper> query = realm.where(FiveDaysWrapper.class).findAll();

        FiveDays fiveDays = new FiveDays();

        City city = new City();
        city.setCountry("Aus");
        city.setName("Sydney");
        fiveDays.setCity(city);

        Lista lista = new Lista();
        Weather weather = new Weather();
        weather.setDescription("Stored windy");
        weather.setMain("Sunny");
        ArrayList<Weather> weatherlist = new ArrayList<>();
        weatherlist.add(weather);
        lista.setWeather(weatherlist);

        ArrayList<Lista> listaList = new ArrayList<>();
        listaList.add(lista);
        fiveDays.setLista(listaList);

        ArrayList<FiveDays> fiveDayses = new ArrayList<>();
        fiveDayses.add(fiveDays);

        return rx.Observable.from(fiveDayses);
    }

    @Override
    public void evictAll() {
        //TODO: clear cache
    }

    @Override
    public void put(FiveDays days) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        TimeStampWrapper lastUpdatedTimeWrapper = realm.where(TimeStampWrapper.class).findFirst();

        realm.createObject(FiveDaysWrapper.class);

        if (lastUpdatedTimeWrapper == null) {
            TimeStampWrapper timeStampWrapper = realm.createObject(TimeStampWrapper.class);
            timeStampWrapper.setLastime(SystemClock.uptimeMillis());
        } else {
            lastUpdatedTimeWrapper.setLastime(SystemClock.uptimeMillis());
        }

        realm.commitTransaction();
    }
}
