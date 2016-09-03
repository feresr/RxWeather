package com.feresr.weather.presenters.views;

import com.feresr.weather.common.BaseView;
import com.feresr.weather.models.Currently;

/**
 * Created by feresr on 24/08/16.
 */
public interface CurrentWeatherView extends BaseView {
    void displayWeather(Currently currently);
}
