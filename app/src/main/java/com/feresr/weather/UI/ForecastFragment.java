package com.feresr.weather.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feresr.weather.R;
import com.feresr.weather.injector.AppComponent;
import com.feresr.weather.models.CityWeather;
import com.feresr.weather.presenters.ForecastPresenter;
import com.feresr.weather.presenters.views.ForecastView;

import javax.inject.Inject;

/**
 * Created by Fernando on 19/10/2015.
 */
public class ForecastFragment extends BaseFragment implements ForecastView {

    private int mScrolledY = 0;

    public static final String ARG_CITY = "city";
    @Inject
    ForecastPresenter presenter;
    private ForecastAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewScrollListener listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        this.getComponent(AppComponent.class).inject(this);
        presenter.attachView(this);
        if (getArguments() != null) {
            presenter.attachIncomingArg(getArguments());
        }
        presenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
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
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ForecastAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().scrollToPosition(0);
        //adapter.showNoInternetWarning();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.scrollToPosition(0);
        listener.onScrolled(0);

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void addForecast(CityWeather s) {
        adapter.addForecast(s);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
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

    public interface RecyclerViewScrollListener {
        void onScrolled(int scrolled);
    }
}
