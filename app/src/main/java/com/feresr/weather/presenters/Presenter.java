package com.feresr.weather.presenters;

import android.os.Bundle;

import com.feresr.weather.presenters.views.View;

/**
 * Created by Fernando on 14/10/2015.
 */
public interface Presenter {

    void onStart();

    void onStop();

    void onPause();

    void attachView(View v);

    void attachIncomingArg(Bundle intent);

    void onCreate();

    void onDestroy();
}