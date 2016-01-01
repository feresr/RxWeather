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
    public static final String PATH_HOUR = "hour";
    public static final String PATH_DAY = "day";

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
        public static final String COLUMN_PRECIP_INTENSITY = "precip_intensity";
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
        public static final String COLUMN_HOURLY_SUMMARY = "hourly_summary";
        public static final String COLUMN_HOURLY_ICON = "hourly_icon";
        public static final String COLUMN_DAILY_SUMMARY = "daily_summary";
        public static final String COLUMN_DAILY_ICON = "daily_icon";
    }

    public static abstract class HourEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HOUR).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HOUR;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HOUR;

        public static final String TABLE_NAME = "hours";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_PRECIP_INTENSITY = "precip_intensity";
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
        public static final String CITY_ID = "city_id";
    }

    public static abstract class DayEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DAY).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DAY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DAY;

        public static final String TABLE_NAME = "days";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_SUNRISE_TIME = "sunrise_time";
        public static final String COLUMN_SUNSET_TIME = "sunset_time";
        public static final String COLUMN_MOON_PHASE = "moon_phase";
        public static final String COLUMN_PRECIP_INTENSITY = "precip_intensity";
        public static final String COLUMN_PRECIP_INTENSITY_MAX = "precip_intensity_max";
        public static final String COLUMN_PRECIP_INTENSITY_MAX_TIME = "precip_intensity_max_time";
        public static final String COLUMN_PRECIP_PROBABILITY = "precip_probability";
        public static final String COLUMN_PRECIP_TYPE = "precip_type";
        public static final String COLUMN_TEMPERATURE_MIN = "temperature_min";
        public static final String COLUMN_TEMPERATURE_MIN_TIME = "temperature_min_time";
        public static final String COLUMN_TEMPERATURE_MAX = "temperature_max";
        public static final String COLUMN_TEMPERATURE_MAX_TIME = "temperature_max_time";
        public static final String COLUMN_APPARENT_TEMPERATURE_MIN= "apparent_temperature_min";
        public static final String COLUMN_APPARENT_TEMPERATURE_MIN_TIME = "apparent_temperature_min_time";
        public static final String COLUMN_APPARENT_TEMPERATURE_MAX = "apparent_temperature_max";
        public static final String COLUMN_APPARENT_TEMPERATURE_MAX_TIME = "apparent_temperature_max_time";
        public static final String COLUMN_DEW_POINT = "dew_point";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_WIND_BEARING = "wind_bearing";
        public static final String COLUMN_CLOUD_COVER = "cloud_cover";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_OZONE = "ozone";
        public static final String CITY_ID = "city_id";
    }
}
