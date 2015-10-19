package com.feresr.rxweather.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feresr.rxweather.R;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.presenters.ForecastPresenter;
import com.feresr.rxweather.presenters.views.ForecastView;

import javax.inject.Inject;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastFragment extends BaseFragment implements ForecastView {

    @Inject
    ForecastPresenter presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        this.getComponent(WeatherApiComponent.class).inject(this);
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void addForecast(String s) {
        Log.e("WEATHER", s);
    }
}
