package com.feresr.weather.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.feresr.weather.DI.HasComponent;
import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.DI.component.DaggerActivityComponent;
import com.feresr.weather.DI.modules.ActivityModule;
import com.feresr.weather.R;
import com.feresr.weather.RxWeatherApplication;

import butterknife.ButterKnife;

/**
 * Created by feresr on 25/07/16.
 * The activity only will execute operations that affect the UI. These operations are defined
 * by a view model and are triggered by its presenter.
 * <p/>
 * Perhaps the activity only will work as a fragment container, if that is the case only
 * return null on {@link BaseActivity#getPresenter()}
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<ActivityComponent> {

    private ActivityComponent activityComponent;

    @Override
    public ActivityComponent getComponent() {
        return activityComponent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // The order is important here, createActivityComponent() MUST be called before super.onCreate()
        // WHY: When "don't keep activities' is enabled, super.OnCreate() will restore fragments by calling
        // fragments.OnCreate() and they'll try to use the ActivityComponent to inject their dependencies.
        // http://stackoverflow.com/questions/14093438/after-the-rotate-oncreate-fragment-is-called-before-oncreate-fragmentactivi
        createActivityComponent();
        injectDependencies(activityComponent);

        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        injectViews();
    }

    /**
     * Setup the object graph and inject the dependencies needed on this activity.
     */
    private void createActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(RxWeatherApplication.getApp(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected abstract void injectDependencies(ActivityComponent activityComponent);

    /**
     * Every object annotated with ButterKnife.bind() its gonna injected trough butterknife
     */
    private void injectViews() {
        ButterKnife.bind(this);
    }

    /**
     * @return The presenter attached to the activity. This must extends from {@link com.productify.urge.common.BasePresenter}
     */
    @Nullable
    protected abstract BasePresenter getPresenter();

    /**
     * @return The layout that's gonna be the activity view.
     */
    protected abstract int getLayout();
}
