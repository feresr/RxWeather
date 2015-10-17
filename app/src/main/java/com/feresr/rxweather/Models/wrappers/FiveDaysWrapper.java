package com.feresr.rxweather.models.wrappers;

import io.realm.RealmObject;

/**
 * Created by Fernando on 16/10/2015.
 */
public class FiveDaysWrapper extends RealmObject {
    private int here = 2;

    public int getHere() {
        return here;
    }

    public void setHere(int here) {
        this.here = here;
    }
}
