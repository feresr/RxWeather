package com.feresr.rxweather.storage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fernando on 27/12/2015.
 */
public final class WeatherContract {

    public static final String CONTENT_AUTHORITY = "com.feresr.rxweather.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CITY = "city";
    public static final String PATH_WEATHER = "weather";

    public WeatherContract() {}


    /* Inner class that defines the table contents */
        public static abstract class CityEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CITY).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITY;

        public static final String TABLE_NAME = "cities";
        public static final String COLUMN_NAME_CITY_NAME = "name";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_LON = "longitude";

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static abstract class WeatherEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

        public static final String TABLE_NAME = "weathers";
        public static final String COLUMN_TIME_ZONE = "time_zone";
        public static final String COLUMN_OFFSET = "offset";
        public static final String COLUMN_FETCH_TIME = "fetch_time";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_PRECIP_INTENSITY = "precip_intencity";
        public static final String COLUMN_PRECIP_PROBABILITY = "precip_probability";
        public static final String COLUMN_PRECIP_TYPE = "precip_type";
        public static final String COLUMN_TEMP = "temperature";
        public static final String COLUMN_APPARENT_TEMP = "apparent_temperature";
        public static final String COLUMN_DEW_POINT = "dew_point";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_WIND_BEARING = "wind_bearing";
        public static final String COLUMN_CLOUD_COVER = "cloud_cover";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_OZONE = "ozone";

    }
}
