package com.feresr.weather.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.feresr.weather.UI.ForecastFragment;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.ForecastView;
import com.feresr.weather.presenters.views.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter implements Presenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private ForecastView forecastView;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    public ForecastPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
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
        City city = (City) bundle.getSerializable(ForecastFragment.ARG_CITY);
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
    public void onDestroy() {
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
