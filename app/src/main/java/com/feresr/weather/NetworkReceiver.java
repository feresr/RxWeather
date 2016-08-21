package com.feresr.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Fernando on 26/12/2015.
 */
public class NetworkReceiver extends BroadcastReceiver {

    public NetworkListener listener;

    public NetworkReceiver() {
        super();
    }

    public void setListener(NetworkListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (listener != null) {
            listener.onNetworkStateChanged(isConnected);
        }
    }
}

