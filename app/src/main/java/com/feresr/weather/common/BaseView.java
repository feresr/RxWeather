package com.feresr.weather.common;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by feresr on 25/07/16.
 * Defines common functionality for a ViewModel (fragment or activity)
 */
public interface BaseView {
    Context getActivity();

    Bundle getArguments();

    void showErrorMessage(String message);
}
