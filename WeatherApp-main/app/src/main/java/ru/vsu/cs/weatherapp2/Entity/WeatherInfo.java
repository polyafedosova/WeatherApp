package ru.vsu.cs.weatherapp2.Entity;

public class WeatherInfo {
    private String temperature;
    private String maxT;
    private String minT;
    private String fillLike;
    private String pressure;
    private String weather;

    private String place;
    public WeatherInfo(double temperature, double maxT, double minT,
                       double fillLike, double pressure, String weather, String place) {
        this.temperature = String.format("%.1f", temperature);
        this.maxT = String.format("%.1f", maxT);
        this.minT = String.format("%.1f", minT);
        this.fillLike = String.format("%.1f", fillLike);
        this.pressure = String.valueOf(pressure);
        this.weather = weather;
        this.place = place;
    }

    public WeatherInfo() {
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMaxT() {
        return maxT;
    }

    public void setMaxT(String maxT) {
        this.maxT = maxT;
    }

    public String getMinT() {
        return minT;
    }

    public void setMinT(String minT) {
        this.minT = minT;
    }

    public String getFillLike() {
        return fillLike;
    }

    public void setFillLike(String fillLike) {
        this.fillLike = fillLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "temperature='" + temperature + '\'' +
                ", maxT='" + maxT + '\'' +
                ", minT='" + minT + '\'' +
                ", fillLike='" + fillLike + '\'' +
                ", pressure='" + pressure + '\'' +
                ", weather='" + weather + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
