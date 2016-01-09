package com.feresr.weather.UI;

import com.feresr.weather.models.City;

/**
 * Created by Fernando on 5/11/2015.
 */
public interface FragmentInteractionsListener {
    void onCitySuggestionSelected(City city);
    void onCitySelected(City city);
    void onAddCityButtonSelected();
}
