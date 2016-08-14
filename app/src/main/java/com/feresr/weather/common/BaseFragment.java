package com.feresr.weather.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feresr.weather.DI.HasComponent;
import com.feresr.weather.DI.component.ActivityComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Fernando on 19/10/2015.
 * * A fragment like an activity only will execute operations that affect the UI.
 * These operations are defined by a view model and are triggered by its presenter.
 */
public abstract class BaseFragment<C extends BasePresenter> extends Fragment {

    @Inject
    protected C presenter;

    public BaseFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(getActivityComponent());
        bindPresenterToView();
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    protected abstract void injectDependencies(ActivityComponent activityComponent);

    protected abstract void bindPresenterToView();

    private void bindViews(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    protected abstract int getFragmentLayout();

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    private ActivityComponent getActivityComponent() {
        try {
            return ((HasComponent<ActivityComponent>) getActivity()).getComponent();
        } catch (ClassCastException e) {
            Log.e(this.getClass().getSimpleName(), "The parent activity for this fragment must implement -HasComponent<ActivityComponent> interface", e);
        }
        return null;
    }
}
