package com.feresr.weather.UI.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.DayForecastAdapter;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.models.Hour;
import com.feresr.weather.models.Hourly;
import com.feresr.weather.presenters.NextHoursPresenter;
import com.feresr.weather.presenters.views.NextHoursView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by feresr on 27/08/16.
 */
public class NextHoursFragment extends BaseFragment<NextHoursPresenter> implements NextHoursView {

    public static final String ARG_HOURLY_WEATHER = "ARG_HOURLY_WEATHER";

    @BindView(R.id.summary)
    TextView summary;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    DayForecastAdapter dayForecastAdapter;

    public static NextHoursFragment newInstance(Hourly hourly) {
        NextHoursFragment fragment = new NextHoursFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_HOURLY_WEATHER, hourly);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dayForecastAdapter = new DayForecastAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dayForecastAdapter);
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
        return R.layout.fragment_next_hours;
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void displayHourlyWeather(Hourly hourly) {
        summary.setText(hourly.getSummary());
        dayForecastAdapter.setData((ArrayList<Hour>) hourly.getData());
    }
}
