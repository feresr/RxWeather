package com.feresr.weather.presenters;

import com.feresr.weather.UI.fragment.NextHoursFragment;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.Hourly;
import com.feresr.weather.presenters.views.NextHoursView;

import javax.inject.Inject;

/**
 * Created by feresr on 27/08/16.
 */
public class NextHoursPresenter extends BasePresenter<NextHoursView> {

    private Hourly hourly;

    @Inject
    public NextHoursPresenter(){
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hourly = (Hourly) view.getArguments().getSerializable(NextHoursFragment.ARG_HOURLY_WEATHER);
    }

    @Override
    public void onStart() {
        super.onStart();
        view.displayHourlyWeather(hourly);
    }
}
