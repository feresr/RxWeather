package com.feresr.rxweather.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.injector.DaggerWeatherApiComponent;
import com.feresr.rxweather.injector.HasComponent;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.injector.modules.ActivityModule;
import com.feresr.rxweather.models.City;

public class WeatherDetailActivity extends AppCompatActivity implements HasComponent<WeatherApiComponent> {

    public static final String ARG_CITY = "city";
    private WeatherApiComponent weatherApiComponent;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(city.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);

        }
        initializeDependencies();
    }

    private void initializeDependencies() {
        weatherApiComponent = DaggerWeatherApiComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((RxWeatherApplication) getApplication()).getAppComponent()).build();
    }

    @Override
    public WeatherApiComponent getComponent() {
        return weatherApiComponent;
    }
}
