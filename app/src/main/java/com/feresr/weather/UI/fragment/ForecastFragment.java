package com.feresr.weather.UI.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.ForecastAdapter;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.DI.component.ApplicationComponent;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.presenters.ForecastPresenter;
import com.feresr.weather.presenters.views.ForecastView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastFragment extends BaseFragment<ForecastPresenter> implements ForecastView {

    private int mScrolledY = 0;

    public static final String ARG_CITY = "city";

    @Inject
    ForecastAdapter adapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.LayoutManager layoutManager;

    private RecyclerViewScrollListener listener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrolledY += dy;
                listener.onScrolled(mScrolledY);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().scrollToPosition(0);
        //adapter.showNoInternetWarning();
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
        return R.layout.fragment_main;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.scrollToPosition(0);
        listener.onScrolled(0);

    }

    @Override
    public void addForecast(CityWeather s) {
        adapter.addForecast(s);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RecyclerViewScrollListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public interface RecyclerViewScrollListener {
        void onScrolled(int scrolled);
    }
}
