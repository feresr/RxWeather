package com.feresr.weather.presenters;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.feresr.weather.R;
import com.feresr.weather.UI.fragment.ForecastFragment;
import com.feresr.weather.common.BaseActivity;
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
