package com.feresr.rxweather.models.wrappers;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Fernando on 16/10/2015.
 */
public class TimeStampWrapper extends RealmObject {
    private long lastime;


    public long getLastime() {
        return lastime;
    }

    public void setLastime(long lastime) {
        this.lastime = lastime;
    }
}
