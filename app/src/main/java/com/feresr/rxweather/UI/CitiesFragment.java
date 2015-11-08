package com.feresr.rxweather.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addCityFab;

    private FragmentInteractionsListener placeSearchListener;

    public CitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        citiesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter = new CitiesAdapter(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        citiesRecyclerView.setLayoutManager(layoutManager);
        citiesRecyclerView.setAdapter(adapter);
        citiesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                placeSearchListener.onCitySelected(adapter.getCities().get(position));

            }
        }));
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.onRemoveCity(adapter.getCities().get(viewHolder.getAdapterPosition()));
                adapter.onItemDismiss(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(citiesRecyclerView);
        addCityFab = (FloatingActionButton) view.findViewById(R.id.add_city_fab);
        addCityFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SearchFragment fragment = new SearchFragment();
                ft.add(R.id.fragment, fragment, null);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            placeSearchListener = (FragmentInteractionsListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCity(City city) {
        adapter.addCity(city);
    }

    @Override
    public void addCities(List<City> cities) {
        adapter.setCities((ArrayList) cities);
    }

    @Override
    public void updateCity(City city) {
        adapter.updateCity(city);
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
        presenter.onCreate();
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
}
