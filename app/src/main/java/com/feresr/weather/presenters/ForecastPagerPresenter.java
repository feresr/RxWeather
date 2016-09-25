package com.feresr.weather.presenters;

import com.feresr.weather.UI.fragment.ForecastPagerFragment;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.ForecastPagerView;

import javax.inject.Inject;

/**
 * Created by feresr on 26/08/16.
 */
public class ForecastPagerPresenter extends BasePresenter<ForecastPagerView> {

    private City city;

    @Inject
    public ForecastPagerPresenter() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        city = (City) view.getArguments().getSerializable(ForecastPagerFragment.ARG_CITY);
    }

    @Override
    public void onStart() {
        super.onStart();
        view.setCityWeather(city.getWeather());
    }
}
