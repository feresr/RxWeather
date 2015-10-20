package com.feresr.rxweather.presenters.views;

import com.feresr.rxweather.models.Day;
import com.feresr.rxweather.models.Today;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface ForecastView extends View {
    void addForecast(Day l);
    void addToday(Today today);
}
