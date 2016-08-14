package com.feresr.weather.presenters;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.feresr.weather.BuildConfig;
import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.SuggestionAdapter;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.SearchView;
import com.feresr.weather.storage.DataCache;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * Created by Fernando on 7/11/2015.
 */
public class SearchPresenter extends BasePresenter<SearchView> implements TextWatcher, SuggestionAdapter.CitySuggestionClickListener {

    private ArrayList<City> cities;
    private AutocompleteFilter filter;
    private FragmentInteractionsListener fragmentInteractionListener;

    private PendingResult<AutocompletePredictionBuffer> result;

    private GoogleApiClient googleApiClient;

    private DataCache storage;

    @Inject
    public SearchPresenter(GoogleApiClient googleApiClient, DataCache storage) {
        super();
        this.googleApiClient = googleApiClient;
        this.storage = storage;
    }

    @Override
    public void onCreate() {
        filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
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
                result.cancel(); //cancel previous request
            }

            result = Places.GeoDataApi.getAutocompletePredictions(googleApiClient, s.toString(),
                    null, filter);

            result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                @Override
                public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {

                    try {
                        cities.clear();
                        for (AutocompletePrediction prediction : autocompletePredictions) {
                            City city = new City();
                            city.setName(prediction.getFullText(null).toString());
                            city.setId(prediction.getPlaceId());
                            cities.add(city);
                        }
                        view.setCities(cities);

                    } catch (Exception e) {
                        Log.e(this.getClass().getSimpleName(), e.getMessage());
                        if (BuildConfig.DEBUG) {
                            view.showErrorMessage(e.getMessage());
                        }
                    } finally {
                        DataBufferUtils.freezeAndClose(autocompletePredictions);
                    }
                }
            });
        } else {
            Log.d(this.getClass().getSimpleName(), "GoogleApiClient not connected");
            if (BuildConfig.DEBUG) {
                view.showErrorMessage("GoogleApiClient not connected");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setFragmentInteractionListener(FragmentInteractionsListener listener) {
        this.fragmentInteractionListener = listener;
    }

    @Override
    public void OnCitySuggestionSelected(City city) {
        storage.putCity(city).subscribe();
        fragmentInteractionListener.onCitySuggestionSelected(city);
    }
}
