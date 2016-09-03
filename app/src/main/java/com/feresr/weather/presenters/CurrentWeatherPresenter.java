package com.feresr.weather.presenters;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.feresr.weather.R;
import com.feresr.weather.UI.fragment.CurrentWeatherFragment;
import com.feresr.weather.common.BaseActivity;
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
        currently = (Currently) view.getArguments().getSerializable(CurrentWeatherFragment.ARG_CURRENT_WEATHER);
    }

    @Override
    public void onStart() {
        view.displayWeather(currently);
    }
}
