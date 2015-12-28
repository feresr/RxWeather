package com.feresr.rxweather.models;

import java.io.Serializable;

/**
 * Created by Fernando on 5/11/2015.
 */
public class City implements Serializable {

    public static final int STATE_DONE = 0;
    public static final int STATE_FETCHING = 1;
    private String id;
    private String name;
    private Double lat;
    private Double lon;

    private int state = STATE_FETCHING;

    private CityWeather cityWeather;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public CityWeather getCityWeather() {
        return cityWeather;
    }

    public void setCityWeather(CityWeather cityWeather) {
        this.cityWeather = cityWeather;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
