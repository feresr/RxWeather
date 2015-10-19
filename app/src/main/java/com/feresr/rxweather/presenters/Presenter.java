package com.feresr.rxweather.presenters;

import android.content.Intent;

import com.feresr.rxweather.presenters.views.View;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Presenter {

    void onStart();

    void onStop();

    void onPause();

    void attachView(View v);

    void attachIncomingIntent(Intent intent);

    void onCreate();
}