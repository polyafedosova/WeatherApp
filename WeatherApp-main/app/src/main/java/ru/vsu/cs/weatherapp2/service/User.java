package ru.vsu.cs.weatherapp2.service;

public class User {
    private String temperature;
    private String wind;
    private String place;
    private String date;

    public User(String temperature, String wind, String place, String date) {
        this.temperature = temperature;
        this.wind = wind;
        this.place = place;
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }
    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
