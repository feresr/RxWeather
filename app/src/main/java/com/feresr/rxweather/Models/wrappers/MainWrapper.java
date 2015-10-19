package com.feresr.rxweather.models.wrappers;

import io.realm.RealmObject;

/**
 * Created by Fernando on 19/10/2015.
 */
public class MainWrapper extends RealmObject{
    private double temp;
    private double tempMax;
    private double tempMin;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }
}
