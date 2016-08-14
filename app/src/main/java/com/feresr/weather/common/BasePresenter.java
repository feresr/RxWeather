package com.feresr.weather.common;

/**
 * Created by feresr on 25/07/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BasePresenter<C extends BaseView> {

    protected C view;

    /**
     * This method will be executed only once per presenter instance
     * Use this method to do any one time initializations.
     * The ModelView (fragment/activity) is Already available at this point BUT its views are not be injected yet
     * {@link BaseFragment#onCreate(Bundle)} ()}  in case presenter is attached to fragment
     */
    public void onCreate() {
    }

    /**
     * This method will be executed on
     * {@link BaseActivity#onStart()} in case presenter is attached to activity <br>
     * {@link BaseFragment#onStart()}  in case presenter is attached to fragment
     */
    public void onStart() {
    }

    /**
     * This method will be executed on
     * {@link AppCompatActivity#onStop()} in case presenter is attached to activity <br>
     * {@link BaseFragment#onStop()}  in case presenter is attached to fragment
     */
    public void onStop() {
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    public void onDestroy() {
        view = null;
    }

    /**
     * Binds the view to this presenter, it is to be called from the fragment's
     * {@link BaseFragment#bindPresenterToView()} ()} method
     *
     * @param view the fragment itself, implementing the corresponding ViewModel C
     */
    public final void setView(C view) {
        this.view = view;
    }
}
