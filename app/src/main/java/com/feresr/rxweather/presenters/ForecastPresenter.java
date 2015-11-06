package com.feresr.rxweather.presenters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.feresr.rxweather.domain.GetForecastUseCase;
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
    private GetForecastUseCase forecastUseCase;
    private Subscription forecastObservable;
    private ForecastView forecastView;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    public ForecastPresenter(GetForecastUseCase forecastUseCase, Context context) {
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
        lat = bundle.getDouble("lat");
        lon = bundle.getDouble("lon");

        forecastUseCase.setLatLon(lat.toString(), lon.toString());

        forecastObservable = forecastUseCase.execute().subscribe(new Subscriber<CityWeather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(CityWeather cityWeather) {
                forecastView.addForecast(cityWeather);
            }
        });
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
