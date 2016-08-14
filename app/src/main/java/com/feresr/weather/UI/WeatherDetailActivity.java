package com.feresr.weather.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.feresr.weather.R;
import com.feresr.weather.RxWeatherApplication;
import com.feresr.weather.DI.component.ApplicationComponent;
import com.feresr.weather.DI.HasComponent;
import com.feresr.weather.UI.fragment.ForecastFragment;
import com.feresr.weather.models.City;

public class WeatherDetailActivity extends AppCompatActivity implements HasComponent<ApplicationComponent>, ForecastFragment.RecyclerViewScrollListener {

    public static final String ARG_CITY = "city";
    private ApplicationComponent weatherApiComponent;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        Intent intent = getIntent();
        City city = (City) intent.getExtras().getSerializable(ARG_CITY);

        if (savedInstanceState == null) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ForecastFragment fragment = new ForecastFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ForecastFragment.ARG_CITY, city);
            fragment.setArguments(bundle);
            ft.add(R.id.container, fragment, null);
            ft.commit();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(city.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);

        }
        initializeDependencies();
    }

    private void initializeDependencies() {
        weatherApiComponent = ((RxWeatherApplication) getApplication()).getComponent();
    }

    @Override
    public ApplicationComponent getComponent() {
        return weatherApiComponent;
    }


    @Override
    public void onScrolled(int scrolled) {
        scrolled /= 2;
        if (scrolled > 255) {
            scrolled = 255;
        }
        toolbar.setBackgroundColor(Color.argb(scrolled,5,5,5));
    }
}
