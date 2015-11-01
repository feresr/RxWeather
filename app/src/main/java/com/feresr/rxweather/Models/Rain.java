package com.feresr.rxweather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fernando on 1/11/2015.
 */
public class Rain {

    @SerializedName("3h")
    @Expose
    private Double accumulatedRain;

    public Rain() {
        super();
    }

    public Double getAccumulatedRain() {
        return accumulatedRain;
    }

    public void setAccumulatedRain(Double accumulatedRain) {
        this.accumulatedRain = accumulatedRain;
    }
}
