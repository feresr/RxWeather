package com.feresr.rxweather;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Fernando on 5/10/2015.
 */
public class WeatherProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.feresr.rxweather.WeatherProvider";

    public static final String URL = "content://" + PROVIDER_NAME + "/weather";
    public static final Uri CONTENT_URL = Uri.parse(URL);

    public static final String temp = "temp";
    public static final String temp_max = "temp_max";
    public static final String temp_min = "temp_min";
    public static final String pressure = "pressure";
    public static final String humidity = "humidity";
    public static final String weather_main = "weather_main";
    public static final String weather_desc = "weather_desc";
    public static final String weather_id = "weather_id";

    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static final int DATABASE_VERSION = 9;
    private static HashMap<String, String> values;
    private static String DATABASE_NAME = "rxWeather";
    private static String TABLE_NAME = "forecast";
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + temp + " TEXT NOT NULL, "
            + temp_max + " TEXT NOT NULL, "
            + temp_min + " TEXT NOT NULL, "
            + pressure + " TEXT NOT NULL, "
            + humidity + " TEXT NOT NULL, "
            + weather_main + " TEXT NOT NULL, "
            + weather_desc + " TEXT NOT NULL, "
            + weather_id + " TEXT NOT NULL);";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "weather", uriCode);
    }

    private SQLiteDatabase sqlDB;

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        return sqlDB != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case uriCode:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri" + uri);
        }

        return queryBuilder.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {

            // vnd.android.cursor.dir/cpcontacts states that we expect multiple pieces of data
            case uriCode:
                return "vnd.android.cursor.dir/weather";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Gets the row id after inserting a map with the keys representing the the column
        // names and their values. The second attribute is used when you try to insert
        // an empty row
        long rowID = sqlDB.insert(TABLE_NAME, null, values);

        // Verify a row has been added
        if (rowID > 0) {

            // Append the given id to the path and return a Builder used to manipulate URI
            // references
            Uri _uri = ContentUris.withAppendedId(CONTENT_URL, rowID);

            // getContentResolver provides access to the content model
            // notifyChange notifies all observers that a row was updated
            getContext().getContentResolver().notifyChange(_uri, null);

            // Return the Builder used to manipulate the URI
            return _uri;
        }
        Toast.makeText(getContext(), "Row Insert Failed", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;

        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            case uriCode:
                rowsDeleted = sqlDB.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // getContentResolver provides access to the content model
        // notifyChange notifies all observers that a row was updated
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;

        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            case uriCode:

                // Update the row or rows of data
                rowsUpdated = sqlDB.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // getContentResolver provides access to the content model
        // notifyChange notifies all observers that a row was updated
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


    // Creates and manages our database
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqlDB) {
            sqlDB.execSQL(CREATE_DB_TABLE);
        }

        // Recreates the table when the database needs to be upgraded
        @Override
        public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
            sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqlDB);
        }
    }
}
