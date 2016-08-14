package com.feresr.weather.presenters.views;

import com.feresr.weather.common.BaseView;
import com.feresr.weather.models.City;

import java.util.ArrayList;

/**
 * Created by Fernando on 7/11/2015.
 */
public interface SearchView extends BaseView {
    void setCities(ArrayList<City> cities);
}
