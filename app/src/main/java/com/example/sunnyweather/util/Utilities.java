package com.example.sunnyweather.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.sunnyweather.db.SunnyWeatherDB;
import com.example.sunnyweather.entity.City;
import com.example.sunnyweather.entity.County;
import com.example.sunnyweather.entity.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    /**
     * 获取省列表
     * @return 所有省的列表，获取失败，返回一个空的列表
     */
    @NonNull
    public static List<Province> getProvinces() {
        ArrayList<Province> provinces = new ArrayList<>();
        // 先从本地数据库获取
        SunnyWeatherDB sunnyWeatherDB = new SunnyWeatherDB(SunnyApplication.getContext(), SunnyWeatherDB.DB_NAME, null, 1);
        try (SQLiteDatabase db = sunnyWeatherDB.getWritableDatabase();
        Cursor cursor = db.query(SunnyWeatherDB.PROVINCE_TABLE_NAME, null, null, null, null, null, null) ) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int code = cursor.getInt(cursor.getColumnIndex("code"));
                provinces.add(new Province(id, name, code));
            }
        }
        // 如果获取不到，从网络中获取，并存入数据库
        if (provinces.isEmpty()) {
            String jsonProvinces = HttpUtil.getProvinces();
            if (jsonProvinces != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonProvinces);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject province = jsonArray.getJSONObject(i);
                        int code = province.getInt("id");
                        String name = province.getString("name");

                        // 先插入数据库中
                        try (SQLiteDatabase db = sunnyWeatherDB.getWritableDatabase()) {
                            ContentValues values = new ContentValues();
                            values.put("code", code);
                            values.put("name", name);
                            long id = db.insert(SunnyWeatherDB.PROVINCE_TABLE_NAME, null, values);
                            // 再插入返回列表中
                            provinces.add(new Province((int) id, name, code));
                        }
                    }
                } catch (JSONException e) {
                    return provinces;
                }
            }
        }
        return provinces;
    }
    public static List<City> getCities(Province province) {
        ArrayList<City> cities = new ArrayList<>();
        // 先从本地数据库获取
        SunnyWeatherDB sunnyWeatherDB = new SunnyWeatherDB(SunnyApplication.getContext(), SunnyWeatherDB.DB_NAME, null, 1);
        try (SQLiteDatabase db = sunnyWeatherDB.getWritableDatabase();
             Cursor cursor = db.query(SunnyWeatherDB.CITY_TABLE_NAME, null, "province_code = ?", new String[] {String.valueOf(province.getCode())}, null, null, null) ) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int code = cursor.getInt(cursor.getColumnIndex("code"));
                cities.add(new City(id, name, code));
            }
        }
        // 如果获取不到，从网络中获取，并存入数据库
        if (cities.isEmpty()) {
            String jsonCities = HttpUtil.getCites(province);
            if (jsonCities != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonCities);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject city = jsonArray.getJSONObject(i);
                        int code = city.getInt("id");
                        String name = city.getString("name");

                        // 先插入数据库中
                        try (SQLiteDatabase db = sunnyWeatherDB.getWritableDatabase()) {
                            ContentValues values = new ContentValues();
                            values.put("code", code);
                            values.put("name", name);
                            values.put("province_code", province.getCode());
                            long id = db.insert(SunnyWeatherDB.CITY_TABLE_NAME, null, values);
                            // 再插入返回列表中
                            cities.add(new City((int) id, name, code));
                        }
                    }
                } catch (JSONException e) {
                    return cities;
                }
            }
        }
        return cities;
    }
    public static List<County> getCounties(Province province, City city) {
        ArrayList<County> counties = new ArrayList<>();
        // 先从本地数据库获取
        SunnyWeatherDB sunnyWeatherDB = new SunnyWeatherDB(SunnyApplication.getContext(), SunnyWeatherDB.DB_NAME, null, 1);
        try (SQLiteDatabase db = sunnyWeatherDB.getWritableDatabase();
             Cursor cursor = db.query(SunnyWeatherDB.COUNTY_TABLE_NAME, null, "city_code = ?", new String[] {String.valueOf(city.getCode())}, null, null, null) ) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int code = cursor.getInt(cursor.getColumnIndex("code"));
                @SuppressLint("Range") String weatherId = cursor.getString(cursor.getColumnIndex("weather_id"));
                counties.add(new County(id, name, code, weatherId));
            }
        }
        // 如果获取不到，从网络中获取，并存入数据库
        if (counties.isEmpty()) {
            String jsonCounties = HttpUtil.getCounties(province, city);
            if (jsonCounties != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonCounties);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject county = jsonArray.getJSONObject(i);
                        int code = county.getInt("id");
                        String name = county.getString("name");
                        String weatherId = county.getString("weather_id");

                        // 先插入数据库中
                        try (SQLiteDatabase db = sunnyWeatherDB.getWritableDatabase()) {
                            ContentValues values = new ContentValues();
                            values.put("code", code);
                            values.put("name", name);
                            values.put("weather_id", weatherId);
                            values.put("city_code", city.getCode());
                            long id = db.insert(SunnyWeatherDB.COUNTY_TABLE_NAME, null, values);
                            // 再插入返回列表中
                            counties.add(new County((int) id, name, code, weatherId));
                        }
                    }
                } catch (JSONException e) {
                    return counties;
                }
            }
        }
        return counties;
    }
}