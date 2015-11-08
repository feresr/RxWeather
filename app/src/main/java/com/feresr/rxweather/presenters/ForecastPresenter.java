package com.feresr.rxweather.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.feresr.rxweather.domain.GetCityForecastUseCase;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.models.CityWeather;
import com.feresr.rxweather.presenters.views.ForecastView;
import com.feresr.rxweather.presenters.views.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter implements Presenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;

    private Double lat;
    private Double lon;
    private GetCityForecastUseCase forecastUseCase;
    private Subscription forecastObservable;
    private ForecastView forecastView;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    public ForecastPresenter(GetCityForecastUseCase forecastUseCase, Context context) {
        this.forecastUseCase = forecastUseCase;
        this.context = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (forecastObservable != null && forecastObservable.isUnsubscribed()) {
            forecastObservable.unsubscribe();
        }
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {
        forecastView = (ForecastView) v;
    }

    @Override
    public void attachIncomingArg(Bundle bundle) {
        City city = (City) bundle.getSerializable("city");
        if (city != null) {
            forecastView.addForecast(city.getCityWeather());
        }
    }

    @Override
    public void onCreate() {

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
//        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
//        if (mLastLocation != null) {
            //forecastUseCase.setLatLon(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));

        //}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("googleapi", connectionResult.toString());
    }
}
