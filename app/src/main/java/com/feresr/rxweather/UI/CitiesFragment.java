package com.feresr.rxweather.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feresr.rxweather.R;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.presenters.CitiesPresenter;
import com.feresr.rxweather.presenters.CitiesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends BaseFragment implements CitiesView {

    @Inject
    CitiesPresenter presenter;

    private RecyclerView citiesRecyclerView;
    private CitiesAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private FloatingActionButton addCityFab;
    private GoogleApiClientProvider googleApiClientProvider;
    private LinearLayout emptyView;
    private SwipeRefreshLayout swipeRefresh;

    private FragmentInteractionsListener fragmentInteractionListener;

    public CitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        citiesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter = new CitiesAdapter(getActivity());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int columns = sharedPref.getBoolean(SettingsActivity.GRIDVIEW, false)? 2 : 1;

        layoutManager = new StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL);
        citiesRecyclerView.setLayoutManager(layoutManager);
        citiesRecyclerView.setAdapter(adapter);

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
        addCityFab = (FloatingActionButton) view.findViewById(R.id.add_city_fab);
        emptyView = (LinearLayout) view.findViewById(R.id.empty_view);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentInteractionListener = (FragmentInteractionsListener) context;
            googleApiClientProvider = (GoogleApiClientProvider) context;
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
        swipeRefresh.setRefreshing(false);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        this.getComponent(WeatherApiComponent.class).inject(this);
        presenter.attachView(this);
        if (getArguments() != null) {
            presenter.attachIncomingArg(getArguments());
        }
        presenter.setAdapter(adapter);
        presenter.setFragmentInteractionListener(fragmentInteractionListener);
        addCityFab.setOnClickListener(presenter);
        citiesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), presenter));
        presenter.setGoogleApiClient(googleApiClientProvider.getApiClient());
        presenter.onCreate();
        swipeRefresh.setOnRefreshListener(presenter);
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
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
