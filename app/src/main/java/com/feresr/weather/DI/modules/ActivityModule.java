package com.feresr.weather.DI.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feresr.weather.DI.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by feresr on 26/07/16.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @ActivityScope
    Context provideActivity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    RecyclerView.LayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}
