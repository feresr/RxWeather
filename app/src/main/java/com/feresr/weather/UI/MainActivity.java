package com.feresr.weather.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.feresr.weather.BuildConfig;
import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.fragment.CitiesFragment;
import com.feresr.weather.UI.fragment.SearchFragment;
import com.feresr.weather.common.BaseActivity;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, FragmentInteractionsListener {

    @Inject
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment, new CitiesFragment(), null);
            ft.commit();
        }

        googleApiClient.registerConnectionCallbacks(this);
        googleApiClient.registerConnectionFailedListener(this);
        googleApiClient.connect();
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        i.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment.class.getName());
        i.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
        startActivity(i);
        return true;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult != null && connectionResult.getErrorMessage() != null) {
            Log.e(MainActivity.this.getClass().getSimpleName(), connectionResult.getErrorMessage());
        }
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
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        SearchFragment fragment = new SearchFragment();
        ft.add(R.id.fragment, fragment, null);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        googleApiClient.unregisterConnectionCallbacks(this);
        googleApiClient.unregisterConnectionFailedListener(this);
        super.onDestroy();
    }
}
