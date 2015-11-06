package com.feresr.rxweather.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.feresr.rxweather.R;
import com.feresr.rxweather.models.City;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements TextWatcher, RecyclerItemClickListener.OnItemClickListener {

    private EditText searchEditText;
    private RecyclerView suggestionsRecyclerView;
    private GoogleApiClientProvider googleApiClientProvider;
    private SuggestionAdapter suggestionAdapter;
    private AutocompleteFilter filter;
    private PendingResult<AutocompletePredictionBuffer> result;
    private FragmentInteractionsListener listener;
    private ArrayList<City> cities;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = (EditText) view.findViewById(R.id.search);
        suggestionsRecyclerView = (RecyclerView) view.findViewById(R.id.suggestions_recyclerview);
        searchEditText.addTextChangedListener(this);
        searchEditText.requestFocus();
        suggestionAdapter = new SuggestionAdapter(getActivity());
        suggestionsRecyclerView.setAdapter(suggestionAdapter);
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        suggestionsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        List<Integer> filterTypes = new ArrayList<>();
        filterTypes.add(com.google.android.gms.location.places.Place.TYPE_GEOCODE);
        filter = AutocompleteFilter.create(filterTypes);
        cities = new ArrayList<>();
        return view;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (googleApiClientProvider.getApiClient().isConnected()) {

            result = Places.GeoDataApi.getAutocompletePredictions(googleApiClientProvider.getApiClient(), s.toString(),
                    null, filter);

            result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                @Override
                public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                    cities.clear();
                    for (AutocompletePrediction prediction : autocompletePredictions) {
                        if (prediction.getPlaceTypes().contains(com.google.android.gms.location.places.Place.TYPE_LOCALITY)) {
                            City city = new City();
                            city.setName(prediction.getDescription());
                            city.setId(prediction.getPlaceId());
                            cities.add(city);
                        }
                    }
                    suggestionAdapter.setCities(cities);

                    DataBufferUtils.freezeAndClose(autocompletePredictions);
                }
            });
        } else {
            Log.d(this.getClass().getSimpleName(), "GoogleApiClient not connected");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            googleApiClientProvider = (GoogleApiClientProvider) context;
            listener = (FragmentInteractionsListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        listener.onCitySuggestionSelected(suggestionAdapter.getCities().get(position));
    }
}
