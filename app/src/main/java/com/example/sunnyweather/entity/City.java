package com.example.sunnyweather.entity;

import java.util.Objects;

public class City {
    private Integer id;
    private String name;
    private Integer code;

    public City() {
    }

    public City(Integer id, String name, Integer code) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) && Objects.equals(name, city.name) && Objects.equals(code, city.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
