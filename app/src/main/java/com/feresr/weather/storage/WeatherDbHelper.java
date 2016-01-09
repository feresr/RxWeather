package com.feresr.weather.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 27/12/2015.
 */
public class WeatherDbHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rxweather.db";
    private static final int DATABASE_VERSION = 1;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database creation sql statement
    private static final String CITY_TABLE_CREATE = "CREATE TABLE " +
            WeatherContract.CityEntry.TABLE_NAME + " (" +
            WeatherContract.CityEntry._ID + " TEXT PRIMARY KEY, " +
            WeatherContract.CityEntry.COLUMN_NAME_CITY_NAME + " TEXT NOT NULL, " +
            WeatherContract.CityEntry.COLUMN_NAME_LAT + " REAL, " +
            WeatherContract.CityEntry.COLUMN_NAME_LON + " REAL);";

    private static final String WEATHER_TABLE_CREATE = "CREATE TABLE " +
            WeatherContract.WeatherEntry.TABLE_NAME + " (" +
            WeatherContract.WeatherEntry._ID + " TEXT PRIMARY KEY REFERENCES " + WeatherContract.CityEntry.TABLE_NAME + "(" + WeatherContract.CityEntry._ID + "), " +
            WeatherContract.WeatherEntry.COLUMN_TIME_ZONE + " TEXT, " +
            WeatherContract.WeatherEntry.COLUMN_OFFSET + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_FETCH_TIME + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_TIME + " NUMBER, " +
            WeatherContract.WeatherEntry.COLUMN_SUMMARY + " TEXT , " +
            WeatherContract.WeatherEntry.COLUMN_ICON + " TEXT NOT NULL, " +
            WeatherContract.WeatherEntry.COLUMN_PRECIP_INTENSITY + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_PRECIP_PROBABILITY + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_PRECIP_TYPE + " TEXT, " +
            WeatherContract.WeatherEntry.COLUMN_TEMP + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_APPARENT_TEMP + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_DEW_POINT + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_WIND_BEARING + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_CLOUD_COVER + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_PRESSURE + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_OZONE + " REAL, " +
            WeatherContract.WeatherEntry.COLUMN_DAILY_SUMMARY + " TEXT, " +
            WeatherContract.WeatherEntry.COLUMN_DAILY_ICON + " TEXT, " +
            WeatherContract.WeatherEntry.COLUMN_HOURLY_SUMMARY + " TEXT, " +
            WeatherContract.WeatherEntry.COLUMN_HOURLY_ICON + " TEXT);";

    private static final String HOURLY_TABLE_CREATE = "CREATE TABLE " +
            WeatherContract.HourEntry.TABLE_NAME + " (" +
            WeatherContract.HourEntry._ID + " INTEGER PRIMARY KEY, " +
            WeatherContract.HourEntry.COLUMN_TIME + " NUMBER, " +
            WeatherContract.HourEntry.COLUMN_SUMMARY + " TEXT, " +
            WeatherContract.HourEntry.COLUMN_ICON + " TEXT, " +
            WeatherContract.HourEntry.COLUMN_PRECIP_INTENSITY + " REAL, " +
            WeatherContract.HourEntry.COLUMN_PRECIP_PROBABILITY + " REAL, " +
            WeatherContract.HourEntry.COLUMN_PRECIP_TYPE + " TEXT, " +
            WeatherContract.HourEntry.COLUMN_TEMP + " REAL, " +
            WeatherContract.HourEntry.COLUMN_APPARENT_TEMP + " REAL, " +
            WeatherContract.HourEntry.COLUMN_DEW_POINT + " REAL, " +
            WeatherContract.HourEntry.COLUMN_HUMIDITY + " REAL, " +
            WeatherContract.HourEntry.COLUMN_WIND_SPEED + " REAL, " +
            WeatherContract.HourEntry.COLUMN_WIND_BEARING + " REAL, " +
            WeatherContract.HourEntry.COLUMN_CLOUD_COVER + " REAL, " +
            WeatherContract.HourEntry.COLUMN_PRESSURE + " REAL, " +
            WeatherContract.HourEntry.COLUMN_OZONE + " REAL, " +
            WeatherContract.HourEntry.CITY_ID + " TEXT REFERENCES " + WeatherContract.CityEntry.TABLE_NAME + " ( " + WeatherContract.CityEntry._ID + " ));";

    private static final String DAYLY_TABLE_CREATE = "CREATE TABLE " +
            WeatherContract.DayEntry.TABLE_NAME + " (" +
            WeatherContract.DayEntry._ID + " INTEGER PRIMARY KEY, " +
            WeatherContract.DayEntry.COLUMN_TIME + " NUMBER, " +
            WeatherContract.DayEntry.COLUMN_SUMMARY + " TEXT, " +
            WeatherContract.DayEntry.COLUMN_ICON + " TEXT, " +
            WeatherContract.DayEntry.COLUMN_SUNRISE_TIME + " INTEGER, " +
            WeatherContract.DayEntry.COLUMN_SUNSET_TIME + " INTEGER, " +
            WeatherContract.DayEntry.COLUMN_MOON_PHASE + " REAL, " +
            WeatherContract.DayEntry.COLUMN_PRECIP_INTENSITY + " REAL, " +
            WeatherContract.DayEntry.COLUMN_PRECIP_INTENSITY_MAX + " REAL, " +
            WeatherContract.DayEntry.COLUMN_PRECIP_INTENSITY_MAX_TIME + " REAL, " +
            WeatherContract.DayEntry.COLUMN_PRECIP_PROBABILITY + " REAL, " +
            WeatherContract.DayEntry.COLUMN_PRECIP_TYPE + " TEXT, " +
            WeatherContract.DayEntry.COLUMN_TEMPERATURE_MAX + " REAL, " +
            WeatherContract.DayEntry.COLUMN_TEMPERATURE_MAX_TIME + " INTEGER, " +
            WeatherContract.DayEntry.COLUMN_TEMPERATURE_MIN + " REAL, " +
            WeatherContract.DayEntry.COLUMN_TEMPERATURE_MIN_TIME + " INTEGER, " +
            WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MAX + " REAL, " +
            WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MAX_TIME + " INTEGER, " +
            WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MIN + " REAL, " +
            WeatherContract.DayEntry.COLUMN_APPARENT_TEMPERATURE_MIN_TIME + " INTEGER, " +
            WeatherContract.DayEntry.COLUMN_DEW_POINT + " REAL, " +
            WeatherContract.DayEntry.COLUMN_HUMIDITY + " REAL, " +
            WeatherContract.DayEntry.COLUMN_WIND_SPEED + " REAL, " +
            WeatherContract.DayEntry.COLUMN_WIND_BEARING + " REAL, " +
            WeatherContract.DayEntry.COLUMN_CLOUD_COVER + " REAL, " +
            WeatherContract.DayEntry.COLUMN_PRESSURE + " REAL, " +
            WeatherContract.DayEntry.COLUMN_OZONE + " REAL, " +
            WeatherContract.DayEntry.CITY_ID + " TEXT REFERENCES " + WeatherContract.CityEntry.TABLE_NAME + " ( " + WeatherContract.CityEntry._ID + " ));";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CITY_TABLE_CREATE);
        db.execSQL(WEATHER_TABLE_CREATE);
        db.execSQL(HOURLY_TABLE_CREATE);
        db.execSQL(DAYLY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WeatherDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.CityEntry.TABLE_NAME);
        onCreate(db);
    }
}
