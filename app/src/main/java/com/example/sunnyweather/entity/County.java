package com.example.sunnyweather.entity;

import java.util.Objects;

public class County {
    private Integer id;
    private String name;
    private Integer code;
    private String weather_id;

    public County(Integer id, String name, Integer code, String weather_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.weather_id = weather_id;
    }

    public County() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof County)) return false;
        County county = (County) o;
        return Objects.equals(id, county.id) && Objects.equals(name, county.name) && Objects.equals(code, county.code) && Objects.equals(weather_id, county.weather_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, weather_id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", weather_id='" + weather_id + '\'' +
                '}';
    }
}
