package com.feresr.weather.UI.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.CitiesPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feresr on 17/09/16.
 */
public class CityFragment extends Fragment {

    private static final String ARG_CITY = "ARG_CITY";
    @BindView(R.id.city_name)
    TextView cityName;
    private CitiesPresenter.CitiesCallbackListener listener;
    private City city;

    public static CityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        city = (City) getArguments().get(ARG_CITY);
        if (city != null) {
            cityName.setText(city.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCitySelected(city);
            }
        });
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CitiesPresenter.CitiesCallbackListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context
                    + " must implement CitiesCallbackListener");
        }
    }

    public City getCity() {
        return city;
    }
}
