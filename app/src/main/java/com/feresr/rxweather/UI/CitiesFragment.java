package com.feresr.rxweather.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feresr.rxweather.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends Fragment {

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

        updateCities();
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

    public void updateCities() {
        adapter.update();
    }
}
