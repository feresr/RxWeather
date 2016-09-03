package com.feresr.weather.presenters.views;

import com.feresr.weather.common.BaseView;
import com.feresr.weather.models.CityWeather;

/**
 * Created by feresr on 26/08/16.
 */
public interface ForecastPagerView extends BaseView {
    void setCityWeather(CityWeather cityWeather);
}
