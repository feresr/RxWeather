package com.feresr.rxweather.storage;

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
            WeatherContract.CityEntry.TABLE_NAME + "(" +
            WeatherContract.CityEntry._ID + " TEXT PRIMARY KEY, " +
            WeatherContract.CityEntry.COLUMN_NAME_CITY_NAME + " TEXT NOT NULL, " +
            WeatherContract.CityEntry.COLUMN_NAME_LAT + " REAL, " +
            WeatherContract.CityEntry.COLUMN_NAME_LON + " REAL);";

    private static final String WEATHER_TABLE_CREATE = "CREATE TABLE " +
            WeatherContract.WeatherEntry.TABLE_NAME + "(" +
            WeatherContract.WeatherEntry._ID + " TEXT PRIMARY KEY REFERENCES "+ WeatherContract.CityEntry.TABLE_NAME + "(" + WeatherContract.CityEntry._ID + "), " +
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CITY_TABLE_CREATE);
        db.execSQL(WEATHER_TABLE_CREATE);
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
