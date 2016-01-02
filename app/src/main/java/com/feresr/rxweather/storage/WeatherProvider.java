package com.feresr.rxweather.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by Fernando on 27/12/2015.
 */
public class WeatherProvider extends ContentProvider {

    static final int WEATHER = 100;
    static final int CITY = 110;
    static final int HOUR = 120;
    static final int DAY = 130;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private WeatherDbHelper mOpenHelper;

    //This UriMatcher will match each URI to the WEATHER integer constants defined above.
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeatherContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, WeatherContract.PATH_CITY, CITY);
        matcher.addURI(authority, WeatherContract.PATH_WEATHER, WEATHER);
        matcher.addURI(authority, WeatherContract.PATH_HOUR, HOUR);
        matcher.addURI(authority, WeatherContract.PATH_DAY, DAY);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case WEATHER:
                return WeatherContract.WeatherEntry.CONTENT_TYPE;
            case CITY:
                return WeatherContract.CityEntry.CONTENT_TYPE;
            case HOUR:
                return WeatherContract.HourEntry.CONTENT_TYPE;
            case DAY:
                return WeatherContract.DayEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "weather"
            case CITY:
                retCursor = mOpenHelper.getReadableDatabase().query(WeatherContract.CityEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null);
                break;
            case WEATHER:
                retCursor = mOpenHelper.getReadableDatabase().query(WeatherContract.WeatherEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null);
                break;
            case HOUR:
                retCursor = mOpenHelper.getReadableDatabase().query(WeatherContract.HourEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null);
                break;
            case DAY:
                retCursor = mOpenHelper.getReadableDatabase().query(WeatherContract.DayEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        long _id;
        switch (match) {
            case CITY:
                _id = db.insertWithOnConflict(WeatherContract.CityEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                if (_id > 0)
                    returnUri = WeatherContract.CityEntry.buildWeatherUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case WEATHER:
                _id = db.insertWithOnConflict(WeatherContract.WeatherEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (_id > 0)
                    returnUri = WeatherContract.CityEntry.buildWeatherUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case DAY:
                _id = db.insertWithOnConflict(WeatherContract.DayEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (_id > 0)
                    returnUri = WeatherContract.CityEntry.buildWeatherUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        final int i = sUriMatcher.match(uri);
        if (null == selection) {
            selection = "1";
        }
        switch (i) {
            case CITY:
                rowsDeleted = db.delete(WeatherContract.CityEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case WEATHER:
                rowsDeleted = db.delete(WeatherContract.WeatherEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case HOUR:
                rowsDeleted = db.delete(WeatherContract.HourEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DAY:
                rowsDeleted = db.delete(WeatherContract.DayEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        final int i = sUriMatcher.match(uri);
        switch (i) {
            case CITY:
                rowsUpdated = db.update(WeatherContract.CityEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WEATHER:
                rowsUpdated = db.update(WeatherContract.WeatherEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case HOUR:
                rowsUpdated = db.update(WeatherContract.HourEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DAY:
                rowsUpdated = db.update(WeatherContract.DayEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        String table;

        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case HOUR:
                table = WeatherContract.HourEntry.TABLE_NAME;
                break;
            case DAY:
                table = WeatherContract.DayEntry.TABLE_NAME;
                break;
            default:
                return -1;
        }

        SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
        sqlDB.beginTransaction();

        try {
            for (ContentValues cv : values) {
                long newID = sqlDB.insertWithOnConflict(table, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            sqlDB.endTransaction();
        }
        return numInserted;
    }
}
