package com.feresr.rxweather.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.feresr.rxweather.R;
import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.injector.DaggerWeatherApiComponent;
import com.feresr.rxweather.injector.HasComponent;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.injector.modules.ActivityModule;

public class WeatherDetailActivity extends AppCompatActivity implements HasComponent<WeatherApiComponent> {

    private WeatherApiComponent weatherApiComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        if (savedInstanceState == null) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ForecastFragment fragment = new ForecastFragment();
            Intent intent = getIntent();

            Bundle bundle = new Bundle();
            bundle.putDouble("lat", intent.getExtras().getDouble("lat"));
            bundle.putDouble("lon", intent.getExtras().getDouble("lon"));

            fragment.setArguments(bundle);
            ft.add(R.id.container, fragment, null);
            ft.commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
