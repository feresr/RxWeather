package com.feresr.rxweather.presenters;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.feresr.rxweather.NetworkListener;
import com.feresr.rxweather.NetworkReceiver;
import com.feresr.rxweather.UI.CitiesAdapter;
import com.feresr.rxweather.UI.FragmentInteractionsListener;
import com.feresr.rxweather.UI.RecyclerItemClickListener;
import com.feresr.rxweather.domain.GetCitiesUseCase;
import com.feresr.rxweather.domain.GetCityForecastUseCase;
import com.feresr.rxweather.domain.RemoveCityUseCase;
import com.feresr.rxweather.domain.SaveCityUseCase;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.presenters.views.View;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fernando on 6/11/2015.
 */
public class CitiesPresenter implements Presenter, NetworkListener, android.view.View.OnClickListener, RecyclerItemClickListener.OnItemClickListener {

    private CitiesAdapter citiesAdapter;
    private GetCityForecastUseCase getCityWeatherUseCase;
    private RemoveCityUseCase removeCityUseCase;
    private CitiesView citiesView;
    private CompositeSubscription subscriptions;
    private GetCitiesUseCase getCitiesUseCase;
    private GoogleApiClient googleApiClient;
    private SaveCityUseCase saveCityUseCase;
    private FragmentInteractionsListener fragmentInteractionListener;
    private NetworkReceiver networkReceiver;
    private Context context;

    @Inject
    public CitiesPresenter(Context context, GetCitiesUseCase getCitiesUseCase, GetCityForecastUseCase getCityForecastUseCase, RemoveCityUseCase removeCityUseCase, SaveCityUseCase saveCityUseCase) {
        super();
        this.getCityWeatherUseCase = getCityForecastUseCase;
        this.saveCityUseCase = saveCityUseCase;
        this.getCitiesUseCase = getCitiesUseCase;
        this.removeCityUseCase = removeCityUseCase;
        this.subscriptions = new CompositeSubscription();
        networkReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiver.setListener(this);
        context.registerReceiver(networkReceiver, intentFilter);
        this.context = context;

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
        citiesView = (CitiesView) v;
    }

    @Override
    public void attachIncomingArg(Bundle intent) {

    }

    public void setAdapter(CitiesAdapter adapter) {
        this.citiesAdapter = adapter;
    }

    @Override
    public void onCreate() {

    }

    private void reloadCities() {
        Subscription subscription = getCitiesUseCase.execute().doOnNext(new Action1<List<City>>() {
            @Override
            public void call(List<City> cities) {
                //Add all cities to view
                citiesView.addCities(cities);
            }
        }).flatMapIterable(new Func1<List<City>, Iterable<City>>() {
            @Override
            public Iterable<City> call(List<City> cities) {
                return cities;
            }
        }).flatMap(new Func1<City, Observable<City>>() {
            @Override
            public Observable<City> call(City city) {
                getCityWeatherUseCase.setCity(city);
                return getCityWeatherUseCase.execute();
            }
        }).subscribe(new Subscriber<City>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //Could not fetch weather
                Log.e(this.getClass().getSimpleName(), e.toString());
            }

            @Override
            public void onNext(City city) {
                city.setState(City.STATE_DONE);
                citiesView.updateCity(city);
            }
        });

        subscriptions.add(subscription);
    }

    @Override
    public void onDestroy() {
        networkReceiver.setListener(null);
        context.unregisterReceiver(networkReceiver);
        subscriptions.unsubscribe();
    }

    public void addNewCity(final City city) {
        //TODO check internet connection (cannot add city to db without lat and lon)
        if (city.getLat() == null || city.getLon() == null) {
            if (googleApiClient.isConnected()) {


                Places.GeoDataApi.getPlaceById(googleApiClient, city.getId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places != null && places.getCount() >= 1 && places.get(0) != null) {
                            city.setState(City.STATE_FETCHING);
                            citiesView.addCity(city);
                            Place place = places.get(0);
                            city.setLat(place.getLatLng().latitude);
                            city.setLon(place.getLatLng().longitude);
                            saveCityUseCase.setCity(city);

                            //Touches UI, we unsubscribe when completed, but also on conf changes (subscription.unsubscribe)
                            subscriptions.add(saveCityUseCase.execute().flatMap(new Func1<City, Observable<City>>() {
                                @Override
                                public Observable<City> call(City city) {
                                    getCityWeatherUseCase.setCity(city);
                                    return getCityWeatherUseCase.execute();
                                }
                            }).subscribe(new Subscriber<City>() {
                                @Override
                                public void onCompleted() {
                                    subscriptions.remove(this);
                                    this.unsubscribe();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(this.getClass().getSimpleName(), e.toString());
                                }

                                @Override
                                public void onNext(City city) {
                                    city.setState(City.STATE_DONE);
                                    citiesView.updateCity(city);
                                }
                            }));

                            DataBufferUtils.freezeAndClose(places);
                        }

                    }
                });
            } else {
                Log.e(this.getClass().getSimpleName(), "GoogleApiClient not connected");
            }
        }
    }

    public void onRemoveCity(City city) {
        removeCityUseCase.setCity(city);

        //Does not touch UI, let it run on the background and un-subscribe by itself
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
                Log.e("onRemoveCity", "city removed");
            }
        });
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onClick(android.view.View v) {
        fragmentInteractionListener.onAddCityButtonSelected();
    }

    public void setFragmentInteractionListener(FragmentInteractionsListener fragmentInteractionListener) {
        this.fragmentInteractionListener = fragmentInteractionListener;
    }

    @Override
    public void onItemClick(android.view.View view, int position) {
        fragmentInteractionListener.onCitySelected(citiesAdapter.getCities().get(position));
    }

    @Override
    public void onNetworkStateChanged(boolean online) {
        if (online) {
            reloadCities();
        }
    }
}
