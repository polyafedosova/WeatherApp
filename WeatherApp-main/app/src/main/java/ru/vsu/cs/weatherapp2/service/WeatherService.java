package ru.vsu.cs.weatherapp2.service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vsu.cs.weatherapp2.Entity.AllWeather;
import ru.vsu.cs.weatherapp2.retrofit.WeatherRetrofit;


public class WeatherService {
    private static final String API_KEY = "5af5b74fe85b0a71a00ddc251b0483d0";
    private static final String BASE_URL = "https://api.openweathermap.org";
    private final Retrofit mRetrofit;

    public WeatherService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<AllWeather> getInfo(String placeName) {
        return this.mRetrofit.create(WeatherRetrofit.class)
                .getPostWithID(placeName, API_KEY);
    }
}
