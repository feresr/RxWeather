package com.feresr.weather.UI;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.feresr.weather.injector.HasComponent;

/**
 * Created by Fernando on 19/10/2015.
 */
public class BaseFragment extends Fragment {
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
}
