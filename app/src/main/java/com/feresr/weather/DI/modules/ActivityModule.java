package com.feresr.weather.DI.modules;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.feresr.weather.DI.ActivityScope;
import com.feresr.weather.UI.CitiesAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import dagger.Module;
import dagger.Provides;

/**
 * Created by feresr on 26/07/16.
 */
@Module
public class ActivityModule {
    private final FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @ActivityScope
    Context provideContext() {
        return this.activity.getBaseContext();
    }

    @Provides
    @ActivityScope
    GoogleApiClient providesGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    @Provides
    @ActivityScope
    CitiesAdapter providesCityAdapter(Context context) {
        return new CitiesAdapter(context);
    }
}
