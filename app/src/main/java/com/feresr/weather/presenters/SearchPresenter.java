package com.feresr.weather.presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.RecyclerItemClickListener;
import com.feresr.weather.UI.SuggestionAdapter;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.SearchView;
import com.google.android.gms.common.ConnectionResult;
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
public class SearchPresenter extends BasePresenter<SearchView> implements TextWatcher, RecyclerItemClickListener.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ArrayList<City> cities;

    private GoogleApiClient googleApiClient;
    private AutocompleteFilter filter;
    private FragmentInteractionsListener fragmentInteractionListener;
    private SuggestionAdapter suggestionAdapter;
    private PendingResult<AutocompletePredictionBuffer> result;

    @Inject
    Context context;

    @Inject
    public SearchPresenter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCreate() {
        filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        cities = new ArrayList<>();
        googleApiClient = new GoogleApiClient.Builder(context, this, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
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
                public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {
                    cities.clear();
                    for (AutocompletePrediction prediction : autocompletePredictions) {
                        City city = new City();
                        city.setName(prediction.getFullText(null).toString());
                        city.setId(prediction.getPlaceId());
                        cities.add(city);
                    }
                    view.setCities(cities);

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(this.getClass().getSimpleName(), connectionResult.getErrorMessage());
    }
}
