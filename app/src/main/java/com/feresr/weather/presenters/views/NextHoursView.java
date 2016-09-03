package com.feresr.weather.presenters.views;

import com.feresr.weather.common.BaseView;
import com.feresr.weather.models.Hourly;

/**
 * Created by feresr on 27/08/16.
 */
public interface NextHoursView extends BaseView {
    void displayHourlyWeather(Hourly hourly);
}
