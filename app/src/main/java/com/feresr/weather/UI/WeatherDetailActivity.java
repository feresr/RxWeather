package com.feresr.weather.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.fragment.ForecastPagerFragment;
import com.feresr.weather.common.BaseActivity;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.utils.IconManager;

public class WeatherDetailActivity extends BaseActivity {

    public static final String ARG_CITY = "city";
    private static final String TAG = WeatherDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        City city = (City) intent.getExtras().getSerializable(ARG_CITY);

        if (city != null && city.getWeather() != null) {
            if (savedInstanceState == null) {
                FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
                ForecastPagerFragment fragment = ForecastPagerFragment.newInstance(city);
                ft.replace(R.id.container, fragment, null);
                ft.commit();
            }

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setBackgroundColor(IconManager.getColorResource(city.getWeather().getCurrently().getIcon(), this));
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(city.getName().split(",")[0]);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setElevation(0);
            }
            findViewById(R.id.layout).setBackgroundColor(IconManager.getColorResource(city.getWeather().getCurrently().getIcon(), this));
        } else {
            Log.e(TAG, "City not found");
            this.finish();
        }
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        //nothing to inject yet
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_weather_detail;
    }

}
