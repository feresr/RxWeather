package com.feresr.rxweather;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            startService(new Intent(this, NetworkService.class));
        }
        updateView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        regiterToCP();
    }

    private void regiterToCP() {
        // Projection contains the columns we want

        // Pass the URL, projection and I'll cover the other options below
        getContentResolver().registerContentObserver(WeatherProvider.CONTENT_URL, true, new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                updateView();
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                updateView();
            }
        });


    }

    private void updateView() {
        String contactList = "";
        String[] projection = new String[]{WeatherProvider.temp, WeatherProvider.weather_main, WeatherProvider.weather_desc};
        Cursor cursor = getContentResolver().query(WeatherProvider.CONTENT_URL, projection, null, null, null);
        // Cycle through and display every row of data
        assert cursor != null;
        if(cursor.moveToFirst()){

            do{

                String id = cursor.getString(cursor.getColumnIndex(WeatherProvider.temp));
                String name = cursor.getString(cursor.getColumnIndex(WeatherProvider.weather_main));
                String weather = cursor.getString(cursor.getColumnIndex(WeatherProvider.weather_desc));

                contactList = contactList + id + " : " + name + " -> "  + weather + "\n";

            }while (cursor.moveToNext());

        }
        cursor.close();
        ((TextView) findViewById(R.id.weather)).setText(contactList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
