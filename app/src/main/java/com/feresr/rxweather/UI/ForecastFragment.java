package com.feresr.rxweather.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feresr.rxweather.R;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.models.Lista;
import com.feresr.rxweather.presenters.ForecastPresenter;
import com.feresr.rxweather.presenters.views.ForecastView;

import javax.inject.Inject;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastFragment extends BaseFragment implements ForecastView {

    @Inject
    ForecastPresenter presenter;

    ForecastAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ForecastAdapter(getActivity(), null);
        recyclerView.setAdapter(adapter);
        return view;
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
    public void addForecast(Lista s) {
        adapter.addForecast(s);
    }
}
