package com.feresr.rxweather.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.feresr.rxweather.R;

/**
 * Created by Fernando on 12/11/2015.
 */
public class IconManager {
    public static String getIconResource(String icon, Context context) {
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

    public static int getColorResource(String icon, Context context) {
        switch (icon) {
            case "rain":
                return ContextCompat.getColor(context, R.color.rain);
            case "clear-day":
                return ContextCompat.getColor(context, R.color.clear_day);
            case "clear-night":
                return ContextCompat.getColor(context, R.color.clear_night);
            case "snow":
                return ContextCompat.getColor(context, R.color.snow);
            case "sleet":
                return ContextCompat.getColor(context, R.color.sleet);
            case "wind":
                return ContextCompat.getColor(context, R.color.wind);
            case "fog":
                return ContextCompat.getColor(context, R.color.fog);
            case "cloudy":
                return ContextCompat.getColor(context, R.color.cloudy);
            case "partly-cloudy-day":
                return ContextCompat.getColor(context, R.color.partly_cloudy_day);
            case "partly-cloudy-night":
                return ContextCompat.getColor(context, R.color.partly_cloudy_night);
            case "hail":
                return ContextCompat.getColor(context, R.color.hail);
            case "thunderstorm":
                return ContextCompat.getColor(context, R.color.thunderstorm);
            case "tornado":
                return ContextCompat.getColor(context, R.color.tornado);
            default:
                return ContextCompat.getColor(context, R.color.clear_day);
        }
    }
}
