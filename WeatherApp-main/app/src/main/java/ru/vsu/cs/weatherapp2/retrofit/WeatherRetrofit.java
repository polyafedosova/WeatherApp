package ru.vsu.cs.weatherapp2.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vsu.cs.weatherapp2.Entity.AllWeather;

public interface WeatherRetrofit {
    @GET("/data/2.5/weather")
    Call<AllWeather> getPostWithID(@Query("q") String cityName,
                                   @Query("appid") String apiKey);
}
