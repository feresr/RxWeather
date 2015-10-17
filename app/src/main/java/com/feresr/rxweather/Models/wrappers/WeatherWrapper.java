package com.feresr.rxweather.models.wrappers;

import io.realm.RealmObject;

/**
 * Created by Fernando on 17/10/2015.
 */
public class WeatherWrapper extends RealmObject {

    private Integer id;

    private String main;

    private String description;

    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
