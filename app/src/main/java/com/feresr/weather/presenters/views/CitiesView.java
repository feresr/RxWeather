package com.feresr.weather.presenters.views;

import com.feresr.weather.common.BaseView;
import com.feresr.weather.models.City;

import java.util.List;

/**
 * Created by Fernando on 6/11/2015.
 */
public interface CitiesView extends BaseView{
    void addCity(City city);
    void addCities(List<City> city);
    void updateCity(City city);
    void showTemperatureInCelsius();
    void showTemperatureInFahrenheit();
    void setSetColumns(int columns);
}
