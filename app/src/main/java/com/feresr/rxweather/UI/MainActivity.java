package com.feresr.rxweather.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.feresr.rxweather.R;
import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.injector.DaggerWeatherApiComponent;
import com.feresr.rxweather.injector.HasComponent;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.injector.modules.ActivityModule;
import com.feresr.rxweather.models.City;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

public class MainActivity extends AppCompatActivity implements HasComponent<WeatherApiComponent>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleApiClientProvider, FragmentInteractionsListener {

    private final boolean DEVELOPER_MODE = true;
    private WeatherApiComponent weatherComponent;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment, new CitiesFragment(), null);
            ft.commit();
        }

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        initializeDependencies();
    }

    private void initializeDependencies() {
        weatherComponent = DaggerWeatherApiComponent.builder().activityModule(new ActivityModule(this))
                .appComponent(((RxWeatherApplication) getApplication()).getAppComponent())
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public WeatherApiComponent getComponent() {
        return weatherComponent;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public GoogleApiClient getApiClient() {
        return googleApiClient;
    }

    @Override
    public void onCitySuggestionSelected(City city) {

        getSupportFragmentManager().popBackStack();

        //Hide soft keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        getSupportFragmentManager().executePendingTransactions();
        ((CitiesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment)).presenter.addNewCity(city);

    }

    @Override
    public void onCitySelected(City city) {
        //if PHONE
        if (city != null && city.getCityWeather() != null) {
            Intent i = new Intent(this, WeatherDetailActivity.class);
            i.putExtra(WeatherDetailActivity.ARG_CITY, city);
            startActivity(i);
        }

        //TODO: if TABLET
        /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ForecastFragment fragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", places.get(0).getLatLng().latitude);
        bundle.putDouble("lon", places.get(0).getLatLng().longitude);

        fragment.setArguments(bundle);
        ft.replace(R.id.fragment, fragment, null);
        ft.addToBackStack(null);
        ft.commit();*/
    }

    @Override
    public void onAddCityButtonSelected() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        ft.add(R.id.fragment, fragment, null);
        ft.addToBackStack(null);
        ft.commit();
    }
}
