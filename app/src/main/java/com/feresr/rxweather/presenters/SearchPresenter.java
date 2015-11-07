package com.feresr.rxweather.presenters;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.feresr.rxweather.UI.FragmentInteractionsListener;
import com.feresr.rxweather.domain.SaveCityUseCase;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.presenters.views.SearchView;
import com.feresr.rxweather.presenters.views.View;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Fernando on 7/11/2015.
 */
public class SearchPresenter implements Presenter, TextWatcher {

    private SearchView searchView;
    private ArrayList<City> cities;
    private AutocompleteFilter filter;
    private PendingResult<AutocompletePredictionBuffer> result;
    private SaveCityUseCase saveCityUseCase;

    @Inject
    public SearchPresenter(SaveCityUseCase saveCityUseCase) {
        super();
        this.saveCityUseCase = saveCityUseCase;
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

    @Override
    public void onCreate() {
        List<Integer> filterTypes = new ArrayList<>();
        filterTypes.add(com.google.android.gms.location.places.Place.TYPE_GEOCODE);
        filter = AutocompleteFilter.create(filterTypes);
        cities = new ArrayList<>();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (searchView.getGoogleApiClient().isConnected()) {

            result = Places.GeoDataApi.getAutocompletePredictions(searchView.getGoogleApiClient(), s.toString(),
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

    public void onCitySuggestionSelected(City city, final FragmentInteractionsListener listener) {
        if (searchView.getGoogleApiClient().isConnected()) {
            Places.GeoDataApi.getPlaceById(searchView.getGoogleApiClient(), city.getId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (places != null && places.get(0) != null) {
                        Place place = places.get(0);
                        saveCityUseCase.setParameters(place.getId(), place.getName().toString(), place.getLatLng().latitude, place.getLatLng().longitude);
                        saveCityUseCase.execute().subscribe(new Subscriber<City>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(City city) {
                                listener.onCitySuggestionSelected(city);
                            }
                        });
                        DataBufferUtils.freezeAndClose(places);

                    }

                }
            });
        }
    }
}
