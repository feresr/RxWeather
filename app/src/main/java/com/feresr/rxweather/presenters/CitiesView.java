package com.feresr.rxweather.presenters;

import com.feresr.rxweather.models.City;
import com.feresr.rxweather.presenters.views.View;

import java.util.List;

/**
 * Created by Fernando on 6/11/2015.
 */
public interface CitiesView extends View {
    void addCity(City city);
    void addCities(List<City> city);
    void updateCity(City city);
}
