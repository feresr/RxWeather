package com.feresr.rxweather.storage;

import android.content.Context;
import android.os.SystemClock;

import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.models.wrappers.FiveDaysWrapper;
import com.feresr.rxweather.models.wrappers.RealmMapper;
import com.feresr.rxweather.models.wrappers.TimeStampWrapper;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by Fernando on 16/10/2015.
 */
@Singleton
public class RealmCache implements DataCache {

    private static final long EXPIRATION_TIME = 30 * 1000;

    private Context context;
    private RealmMapper mapper;

    @Inject
    public RealmCache(Context context, RealmMapper mapper) {
        this.context = context;
        this.mapper = mapper;
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
        FiveDaysWrapper query = realm.where(FiveDaysWrapper.class).findFirst();

        ArrayList<FiveDays> fiveDayses = new ArrayList<>();
        fiveDayses.add(mapper.convert(query));
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

        FiveDaysWrapper fiveDaysWrapper = realm.where(FiveDaysWrapper.class).findFirst();
        if (fiveDaysWrapper == null) {
            fiveDaysWrapper = realm.createObject(FiveDaysWrapper.class);
        }

        for (Lista l :
                days.getLista()) {
            fiveDaysWrapper.getLista().add(mapper.convert(l));
        }

        TimeStampWrapper lastUpdatedTimeWrapper = realm.where(TimeStampWrapper.class).findFirst();

        if (lastUpdatedTimeWrapper == null) {
            TimeStampWrapper timeStampWrapper = realm.createObject(TimeStampWrapper.class);
            timeStampWrapper.setLastime(SystemClock.uptimeMillis());
        } else {
            lastUpdatedTimeWrapper.setLastime(SystemClock.uptimeMillis());
        }

        realm.commitTransaction();
    }
}
