package com.feresr.weather.UI.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.CitiesAdapter;
import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.SettingsActivity;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.CitiesPresenter;
import com.feresr.weather.presenters.views.CitiesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends BaseFragment<CitiesPresenter> implements CitiesView {

    @BindView(R.id.recyclerview)
    RecyclerView citiesRecyclerView;

    @Inject
    CitiesAdapter adapter;

    @Inject
    SharedPreferences sharedPreferences;
    @BindView(R.id.add_city_fab)
    FloatingActionButton addCityFab;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    private StaggeredGridLayoutManager layoutManager;
    private FragmentInteractionsListener fragmentInteractionListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int columns = sharedPreferences.getBoolean(SettingsActivity.GRIDVIEW, false) ? 2 : 1;

        layoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        citiesRecyclerView.setLayoutManager(layoutManager);
        citiesRecyclerView.setAdapter(adapter);
        adapter.setListener(presenter);
        citiesRecyclerView.setVisibility(View.VISIBLE);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.onRemoveCity(adapter.getCities().get(viewHolder.getAdapterPosition()));
                adapter.onItemDismiss(viewHolder.getAdapterPosition());
                if (adapter.getCities().isEmpty()) {
                    citiesRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(citiesRecyclerView);

        addCityFab.setOnClickListener(presenter);
        //citiesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), presenter));
        //presenter.setGoogleApiClient(googleApiClientProvider.getApiClient());
        swipeRefresh.setOnRefreshListener(presenter);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentInteractionListener = (FragmentInteractionsListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCity(City city) {
        adapter.addCity(city);
        citiesRecyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void addCities(List<City> cities) {
        adapter.setCities((ArrayList) cities);
        if (cities != null && !cities.isEmpty()) {
            citiesRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
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
    public void setSetColumns(int columns) {
        if (columns >= 2) {
            adapter.setCompactView(true);
        } else {
            adapter.setCompactView(false);
        }
        layoutManager.setSpanCount(columns);
    }

    @Override
    public void hideLoadingIndicator() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
