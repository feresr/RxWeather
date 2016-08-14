package com.feresr.weather.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.presenters.views.ForecastView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

/**
 * Created by Fernando on 14/10/2015.
 */
public class ForecastPresenter extends BasePresenter<ForecastView> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;

    @Inject
    public ForecastPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

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
