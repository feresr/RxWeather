package com.feresr.rxweather.utils;

import android.content.Context;

import com.feresr.rxweather.R;

/**
 * Created by Fernando on 12/11/2015.
 */
public class IconManager {
    public static String getIconFromString(String icon, Context context) {
        switch (icon) {
            case "rain":
                return context.getString(R.string.rain);
            case "clear-day":
                return context.getString(R.string.day_sunny);
            case "clear-night":
                return context.getString(R.string.clear_night);
            case "snow":
                return context.getString(R.string.snow);
            case "sleet":
                return context.getString(R.string.sleet);
            case "wind":
                return context.getString(R.string.strong_wind);
            case "fog":
                return context.getString(R.string.fog);
            case "cloudy":
                return context.getString(R.string.cloudy);
            case "partly-cloudy-day":
                return context.getString(R.string.day_cloudy);
            case "partly-cloudy-night":
                return context.getString(R.string.night_cloudy);
            case "hail":
                return context.getString(R.string.day_hail);
            case "thunderstorm":
                return context.getString(R.string.thunderstorms);
            case "tornado":
                return context.getString(R.string.tornado);
            default:
                return context.getString(R.string.day_sunny);
        }
    }
}
