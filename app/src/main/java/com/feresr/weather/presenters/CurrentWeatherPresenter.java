package com.feresr.weather.presenters;

import com.feresr.weather.UI.fragment.NowWeatherFragment;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.Currently;
import com.feresr.weather.presenters.views.CurrentWeatherView;

import javax.inject.Inject;

/**
 * Created by feresr on 24/08/16.
 */
public class CurrentWeatherPresenter extends BasePresenter<CurrentWeatherView> {

    private Currently currently;

    @Inject
    public CurrentWeatherPresenter() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        currently = (Currently) view.getArguments().getSerializable(NowWeatherFragment.ARG_CURRENT_WEATHER);
    }

    @Override
    public void onStart() {
        view.displayWeather(currently);
    }
}
