package com.example.sunnyweather.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sunnyweather.entity.City;
import com.example.sunnyweather.entity.Province;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    private static final String BASE_URL = "http://guolin.tech/api/china";
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * @return 获取失败，返回null
     */
    @Nullable
    public static String getProvinces() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Nullable
    public static String getCites(Province province) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + province.getCode())
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCounties(Province province, City city) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/" + province.getCode() + "/" + city.getCode())
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
