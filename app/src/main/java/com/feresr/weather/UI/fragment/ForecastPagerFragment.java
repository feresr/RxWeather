package com.feresr.weather.UI.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.ForecastAdapter;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.models.City;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.presenters.ForecastPagerPresenter;
import com.feresr.weather.presenters.views.ForecastPagerView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by feresr on 26/08/16.
 */
public class ForecastPagerFragment extends BaseFragment<ForecastPagerPresenter> implements ForecastPagerView {


    public static final String ARG_CITY = "CITY";

    @BindView(R.id.forecast_pager)
    ViewPager forecastPager;

    @Inject
    ForecastAdapter forecastAdapter;

    public static ForecastPagerFragment newInstance(City city) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        ForecastPagerFragment fragment = new ForecastPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forecastAdapter = new ForecastAdapter(getActivity().getSupportFragmentManager());
        forecastPager.setOffscreenPageLimit(2);
        forecastPager.setAdapter(forecastAdapter);
        forecastPager.setPageMargin(-180);
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void bindPresenterToView() {
        presenter.setView(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_forecast_pager;
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void setCityWeather(CityWeather cityWeather) {
        forecastAdapter.setCityWeather(cityWeather);
    }
}
