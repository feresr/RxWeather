package com.feresr.rxweather.presenters.views;

import com.feresr.rxweather.models.City;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by Fernando on 7/11/2015.
 */
public interface SearchView extends View {
    GoogleApiClient getGoogleApiClient();
    void setCities(ArrayList<City> cities);
}
