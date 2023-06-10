package ru.vsu.cs.weatherapp2.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.weatherapp2.service.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "city_weather.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "city_weather";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_WIND = "pressure";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_TEMPERATURE + " TEXT, " +
                COLUMN_WIND + " TEXT, " +
                COLUMN_PLACE + " TEXT, " +
                COLUMN_DATE + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
    public void insertCityWeather(User cityWeather) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMPERATURE, cityWeather.getTemperature());
        values.put(COLUMN_WIND, cityWeather.getWind());
        values.put(COLUMN_PLACE, cityWeather.getPlace());
        values.put(COLUMN_DATE, cityWeather.getDate());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public List<User> getAllCityWeather() {
        List<User> cityWeatherList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String temperature = cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE));
                @SuppressLint("Range") String wind = cursor.getString(cursor.getColumnIndex(COLUMN_WIND));
                @SuppressLint("Range") String place = cursor.getString(cursor.getColumnIndex(COLUMN_PLACE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                User user = new User(temperature, wind, place, date);
                cityWeatherList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cityWeatherList;
    }
}

