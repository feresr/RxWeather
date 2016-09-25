package com.feresr.weather.UI.fragment;

import android.animation.ArgbEvaluator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.CitiesAdapter;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.CitiesPresenter;
import com.feresr.weather.presenters.views.CitiesView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends BaseFragment<CitiesPresenter> implements CitiesView, ViewPager.OnPageChangeListener {

    CitiesAdapter adapter;

    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Inject
    SharedPreferences sharedPreferences;
    @BindView(R.id.add_city_fab)
    FloatingActionButton addCityFab;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    @BindView(R.id.city_background)
    View cityBackground;

    @BindView(R.id.city_pager)
    ViewPager citiesPager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CitiesAdapter(getActivity().getSupportFragmentManager(), getContext());
        citiesPager.setAdapter(adapter);
        citiesPager.setVisibility(View.VISIBLE);
        addCityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddCitySelected();
            }
        });
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
        return R.layout.fragment_cities;
    }

    @Override
    public void addCity(City city) {
        adapter.addCity(city);
        citiesPager.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void updateCity(City city) {
        adapter.updateCity(city);
    }

    @Override
    public void showTemperatureInCelsius() {
        adapter.showTemperaturesInCelsius();
    }

    @Override
    public void showTemperatureInFahrenheit() {
        adapter.showTemperaturesInFahrenheit();
    }

    @Override
    public void hideLoadingIndicator() {
        //swipeRefresh.setRefreshing(false);
    }

    @Override
    public void completed(ArrayList<City> cities) {
        citiesPager.addOnPageChangeListener(this);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position < adapter.getCount() - 1) {
            citiesPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, adapter.getCityColor(position), adapter.getCityColor(position + 1)));
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
