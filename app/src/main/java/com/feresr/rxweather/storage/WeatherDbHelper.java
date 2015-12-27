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
    private static final String DATABASE_CREATE = "CREATE TABLE " +
            WeatherContract.CityEntry.TABLE_NAME + "(" +
            WeatherContract.CityEntry._ID + " TEXT PRIMARY KEY, " +
            WeatherContract.CityEntry.COLUMN_NAME_CITY_NAME + " TEXT NOT NULL, " +
            WeatherContract.CityEntry.COLUMN_NAME_LAT + " REAL, " +
            WeatherContract.CityEntry.COLUMN_NAME_LON + " REAL);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
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
