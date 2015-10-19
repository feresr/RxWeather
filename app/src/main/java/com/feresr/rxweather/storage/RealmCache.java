package com.feresr.rxweather.storage;

import android.content.Context;
import android.os.SystemClock;

import com.feresr.rxweather.models.FiveDays;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.models.wrappers.FiveDaysWrapper;
import com.feresr.rxweather.models.wrappers.RealmMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Fernando on 16/10/2015.
 */
@Singleton
public class RealmCache implements DataCache {

    private static final long EXPIRATION_TIME = 30 * 1000;

    private Context context;
    private RealmMapper mapper;
    private long lastUpdated = 0;

    @Inject
    public RealmCache(Context context, RealmMapper mapper) {
        this.context = context;
        this.mapper = mapper;
    }

    @Override
    public boolean isExpired() {


        if (lastUpdated == 0) {
            return true;
        }


        boolean expired = (SystemClock.uptimeMillis() - lastUpdated > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public rx.Observable<FiveDays> get() {
        return Observable.create(new Observable.OnSubscribe<FiveDays>() {
            @Override
            public void call(Subscriber<? super FiveDays> subscriber) {
                Realm realm = Realm.getInstance(context);
                FiveDaysWrapper query = realm.where(FiveDaysWrapper.class).findFirst();
                subscriber.onNext(mapper.convert(query));
                realm.close();
                subscriber.onCompleted();

            }
        });
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

        fiveDaysWrapper.getLista().clear();
        for (Lista l :
                days.getLista()) {
            fiveDaysWrapper.getLista().add(mapper.convert(l));
        }
        realm.commitTransaction();

        lastUpdated = SystemClock.uptimeMillis();

        realm.close();
    }
}
