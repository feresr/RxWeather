package com.feresr.rxweather.UI;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.feresr.rxweather.R;
import com.feresr.rxweather.RxWeatherApplication;
import com.feresr.rxweather.injector.DaggerWeatherApiComponent;
import com.feresr.rxweather.injector.HasComponent;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.injector.modules.ActivityModule;

public class MainActivity extends AppCompatActivity implements HasComponent<WeatherApiComponent> {

    private final boolean DEVELOPER_MODE = true;

    private TextView text;
    private WeatherApiComponent weatherComponent;

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
        text = (TextView) findViewById(R.id.weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDependencies();
    }

    private void initializeDependencies() {
        weatherComponent = DaggerWeatherApiComponent.builder().activityModule(new ActivityModule(this))
                .appComponent(((RxWeatherApplication) getApplication()).getAppComponent())
                .build();
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
}
