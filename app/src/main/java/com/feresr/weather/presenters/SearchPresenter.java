package com.feresr.weather.presenters;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.RecyclerItemClickListener;
import com.feresr.weather.UI.SuggestionAdapter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.SearchView;
import com.feresr.weather.presenters.views.View;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Fernando on 7/11/2015.
 */
public class SearchPresenter implements Presenter, TextWatcher, RecyclerItemClickListener.OnItemClickListener {

    private SearchView searchView;
    private ArrayList<City> cities;
    private AutocompleteFilter filter;
    private PendingResult<AutocompletePredictionBuffer> result;

    private GoogleApiClient googleApiClient;
    private FragmentInteractionsListener fragmentInteractionListener;
    private SuggestionAdapter suggestionAdapter;

    @Inject
    public SearchPresenter() {
        super();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        searchView = (SearchView) v;
    }

    @Override
    public void attachIncomingArg(Bundle intent) {

    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onCreate() {
        List<Integer> filterTypes = new ArrayList<>();
        filterTypes.add(com.google.android.gms.location.places.Place.TYPE_GEOCODE);
        filter = AutocompleteFilter.create(filterTypes);
        cities = new ArrayList<>();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (googleApiClient.isConnected()) {
            if (result != null) {
                result.cancel();
            }
            result = Places.GeoDataApi.getAutocompletePredictions(googleApiClient, s.toString(),
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
                    searchView.setCities(cities);

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

    private void onCitySuggestionSelected(final City city) {
        fragmentInteractionListener.onCitySuggestionSelected(city);
    }

    @Override
    public void onItemClick(android.view.View view, int position) {
        if (position == -1) {
            return;
        }
        onCitySuggestionSelected(suggestionAdapter.getCities().get(position));
    }


    public void setSuggestionAdapter(SuggestionAdapter adapter) {
        this.suggestionAdapter = adapter;
    }

    public void setFragmentInteractionListener(FragmentInteractionsListener listener) {
        this.fragmentInteractionListener = listener;
    }
}
