package com.example.sunnyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SunnyWeatherDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "SunnyWeather";
    public static final String PROVINCE_TABLE_NAME = "province";
    public static final String CITY_TABLE_NAME = "city";
    public static final String COUNTY_TABLE_NAME = "county";
    private static final String PROVINCE_TABLE = "create table province(" +
            "id integer primary key autoincrement," +
            "name text not null," +
            "code integer not null)";
    private static final String CITY_TABLE = "create table city(" +
            "id integer primary key autoincrement," +
            "name text not null," +
            "code integer not null," +
            "province_code integer not null)";
    private static final String COUNTY_TABLE = "create table county(" +
            "id integer primary key autoincrement," +
            "name text not null," +
            "code integer not null," +
            "weather_id text not null," +
            "city_code integer not null)";
    public SunnyWeatherDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROVINCE_TABLE);
        db.execSQL(CITY_TABLE);
        db.execSQL(COUNTY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
