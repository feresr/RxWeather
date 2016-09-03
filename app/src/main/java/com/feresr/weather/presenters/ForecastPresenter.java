package com.feresr.weather.presenters;

import com.feresr.weather.UI.fragment.ForecastFragment;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.ForecastView;

import javax.inject.Inject;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter extends BasePresenter<ForecastView> {

    @Inject
    public ForecastPresenter() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        City city = (City) view.getArguments().getSerializable(ForecastFragment.ARG_CITY);
        if (city != null) {
            view.addForecast(city.getCityWeather());
        } else {
            view.showErrorMessage("City not found");
        }
    }
}
