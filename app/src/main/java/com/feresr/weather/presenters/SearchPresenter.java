package com.feresr.weather.presenters;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.feresr.weather.BuildConfig;
import com.feresr.weather.UI.SuggestionAdapter;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.SearchView;
import com.feresr.weather.usecase.SaveCityUseCase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;


/**
 * Created by Fernando on 7/11/2015.
 */
public class SearchPresenter extends BasePresenter<SearchView> implements TextWatcher, SuggestionAdapter.CitySuggestionClickListener {

    private ArrayList<City> cities;
    private AutocompleteFilter filter;
    private CitySearchCallbackListener fragmentInteractionListener;

    private PendingResult<AutocompletePredictionBuffer> result;

    private GoogleApiClient googleApiClient;

    private SaveCityUseCase saveCityUseCase;

    @Inject
    public SearchPresenter(GoogleApiClient googleApiClient, SaveCityUseCase saveCityUseCase) {
        super();
        this.googleApiClient = googleApiClient;
        this.saveCityUseCase = saveCityUseCase;
    }

    @Override
    public void onCreate() {
        filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        cities = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (view.getActivity() instanceof CitySearchCallbackListener) {
            fragmentInteractionListener = (CitySearchCallbackListener) view.getActivity();
        } else {
            throw new RuntimeException(view.getActivity().toString()
                    + " must implement CitySearchCallbackListener");
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
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
    public void onCitySuggestionSelected(final City city) {
        view.showLoadingView();
        Places.GeoDataApi.getPlaceById(googleApiClient, city.getId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                try {
                    LatLng latLng = places.get(0).getLatLng();
                    city.setLat(latLng.latitude);
                    city.setLon(latLng.longitude);

                    saveCityUseCase.setCity(city);
                    saveCityUseCase.execute().subscribe(new Subscriber<City>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(City city) {

                        }
                    });
                    fragmentInteractionListener.onCitySuggestionSelected(city);

                } catch (Exception e) {
                    e.printStackTrace();
                    view.hideLoadingView();
                    view.showErrorMessage("message:" + e.getMessage());
                } finally {
                    DataBufferUtils.freezeAndClose(places);
                }
            }
        });
    }

    public interface CitySearchCallbackListener {
        void onCitySuggestionSelected(City city);
    }
}
