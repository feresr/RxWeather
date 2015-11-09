package com.feresr.rxweather.presenters;

import android.os.Bundle;
import android.util.Log;

import com.feresr.rxweather.domain.GetCitiesUseCase;
import com.feresr.rxweather.domain.GetCityForecastUseCase;
import com.feresr.rxweather.domain.RemoveCityUseCase;
import com.feresr.rxweather.domain.SaveCityUseCase;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.presenters.views.View;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fernando on 6/11/2015.
 */
public class CitiesPresenter implements Presenter {

    private GetCityForecastUseCase getCityWeatherUseCase;
    private RemoveCityUseCase removeCityUseCase;
    private CitiesView citiesView;
    private CompositeSubscription subscriptions;
    private GetCitiesUseCase getCitiesUseCase;
    private GoogleApiClient googleApiClient;
    private SaveCityUseCase saveCityUseCase;

    @Inject
    public CitiesPresenter(GetCitiesUseCase getCitiesUseCase, GetCityForecastUseCase getCityForecastUseCase, RemoveCityUseCase removeCityUseCase, SaveCityUseCase saveCityUseCase) {
        super();
        this.getCityWeatherUseCase = getCityForecastUseCase;
        this.saveCityUseCase = saveCityUseCase;
        this.getCitiesUseCase = getCitiesUseCase;
        this.removeCityUseCase = removeCityUseCase;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        citiesView = (CitiesView) v;
    }

    @Override
    public void attachIncomingArg(Bundle intent) {

    }


    @Override
    public void onCreate() {

        Subscription subscription = getCitiesUseCase.execute().subscribe(new Subscriber<List<City>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(final List<City> cities) {
                citiesView.addCities(cities);
                for (City city : cities) {
                    getCityWeatherUseCase.setLatLon(city.getLat().toString(), city.getLon().toString(), city.getId());
                    getCityWeather(city);
                }
            }
        });

        subscriptions.add(subscription);
    }

    public void addNewCity(final City city) {
        citiesView.addCity(city);
        if (city.getLat() == null || city.getLon() == null) {
            if (googleApiClient.isConnected()) {

                Places.GeoDataApi.getPlaceById(googleApiClient, city.getId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places != null && places.get(0) != null) {

                            Place place = places.get(0);
                            saveCityUseCase.setParameters(place.getId(), place.getName().toString(), place.getLatLng().latitude, place.getLatLng().longitude);
                            saveCityUseCase.execute().subscribe(new Subscriber<City>() {
                                @Override
                                public void onCompleted() {
                                    this.unsubscribe();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error", e.toString());
                                }

                                @Override
                                public void onNext(City city) {
                                    getCityWeatherUseCase.setLatLon(city.getLat().toString(), city.getLon().toString(), city.getId());
                                    getCityWeather(city);
                                }
                            });
                            DataBufferUtils.freezeAndClose(places);

                        }

                    }
                });
            } else {
                Log.e(this.getClass().getSimpleName(), "GoogleApiClient not connected");
            }
        }
    }

    private void getCityWeather(final City city) {
        subscriptions.add(getCityWeatherUseCase.execute().subscribe(new Subscriber<CityWeather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(CityWeather cityWeather) {
                city.setCityWeather(cityWeather);
                citiesView.updateCity(city);
            }
        }));
    }

    public void onRemoveCity(City city) {
        removeCityUseCase.setCity(city);
        removeCityUseCase.execute().subscribe(new Subscriber<City>() {
            @Override
            public void onCompleted() {
                this.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(City city) {
                //City deleted
            }
        });
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }
}
