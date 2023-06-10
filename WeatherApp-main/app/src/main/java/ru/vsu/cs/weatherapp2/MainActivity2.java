package ru.vsu.cs.weatherapp2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vsu.cs.weatherapp2.Entity.AllWeather;
import ru.vsu.cs.weatherapp2.db.DatabaseHelper;
import ru.vsu.cs.weatherapp2.service.User;
import ru.vsu.cs.weatherapp2.service.WeatherService;

public class MainActivity2 extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private WeatherService service;
    private Boolean checkSaveUserToDatabase;
    private Integer iconResourceId;

    TextView city, country, time, temp, forecast, humidity, min_temp, max_temp, sunrises, sunsets;
    SearchView searchV;
    LinearLayout linearLayout;
    ImageView history, icon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        history = findViewById(R.id.historyButton);
        icon = findViewById(R.id.icon);

        history.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        });

        linearLayout = findViewById(R.id.contentBlock);
        searchV = findViewById(R.id.searchView);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        time = findViewById(R.id.time);
        temp = findViewById(R.id.temp);
        forecast = findViewById(R.id.forecast);
        humidity = findViewById(R.id.humidity);
        min_temp = findViewById(R.id.min_temp);
        max_temp = findViewById(R.id.max_temp);
        sunrises = findViewById(R.id.sunrises);
        sunsets = findViewById(R.id.sunsets);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        service = new WeatherService();

        List<User> userList = databaseHelper.getAllCityWeather();
        if (!userList.isEmpty()) {
            User lastUser = userList.get(userList.size() - 1);
            String lastPlace = lastUser.getPlace();
            getWeatherInfo(lastPlace);
            checkSaveUserToDatabase = false;
        }

        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getWeatherInfo(query);
                searchV.clearFocus();
                checkSaveUserToDatabase = true;
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    public void getWeatherInfo(String placeName) {
        service.getInfo(placeName).enqueue(new Callback<AllWeather>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onResponse(Call<AllWeather> call, Response<AllWeather> response) {

                if (response.isSuccessful()) {
                    linearLayout.setVisibility(View.VISIBLE);
                    AllWeather weather = response.body();

                    if (weather != null) {

                        city.setText(placeName);
                        country.setText(weather.getSys().getCountry());

                        Long lasd = Long.valueOf(weather.getDt());
                        String updatedAtText = "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).
                                format(new Date(lasd * 1000));
                        time.setText(updatedAtText);

                        String weatherIconCode = weather.getWeather().get(0).getIcon();
                        installIcon(weatherIconCode);
                        icon.setImageResource(iconResourceId);

                        temp.setText(String.format("%.1f", (weather.getMain().getTemp() - 273)) + "°C");
                        forecast.setText(weather.getWeather().get(0).getDescription());
                        humidity.setText(String.valueOf(weather.getMain().getHumidity()));
                        min_temp.setText(String.format("%.1f", (weather.getMain().getTempMin() - 273)));
                        max_temp.setText(String.format("%.1f", (weather.getMain().getTempMax() - 273)));

                        String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                .format(new Date(weather.getSys().getSunrise() * 1000));
                        sunrises.setText(sunrise);

                        String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).
                                format(new Date(weather.getSys().getSunset() * 1000));
                        sunsets.setText(sunset);

                        String temperature = temp.getText().toString();
                        String wind = humidity.getText().toString();
                        String place = city.getText().toString();
                        String date = getCurrentDate();

                        User user = new User(temperature, wind, place, date);

                        if (checkSaveUserToDatabase){
                            saveUserToDatabase(user);
                            Toast.makeText(MainActivity2.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(MainActivity2.this, "Такого места нет!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AllWeather> call, Throwable t) {
                t.fillInStackTrace();
            }
        });
    }
    private void saveUserToDatabase(User user) {
        databaseHelper.insertCityWeather(user);
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

    private int installIcon (String st){
        switch (st){
            case "01d":
                iconResourceId = R.drawable.day;
                break;
            case "01n":
                iconResourceId = R.drawable.night;
                break;
            case "02d":
                iconResourceId = R.drawable.icon2d;
                break;
            case "02n":
                iconResourceId = R.drawable.icon2n;
                break;
            case "03d":
            case "03n":
                iconResourceId = R.drawable.icon3;
                break;
            case "04d":
            case "04n":
                iconResourceId = R.drawable.icon4;
                break;
            case "09d":
            case "09n":
                iconResourceId = R.drawable.icon4;
                break;
            case "10d":
                iconResourceId = R.drawable.raind;
                break;
            case "10n":
                iconResourceId = R.drawable.rainn;
                break;
            case "11d":
            case "11n":
                iconResourceId = R.drawable.icon11;
                break;
            case "13d":
            case "13n":
                iconResourceId = R.drawable.snow;
                break;
            case "50d":
            case "50n":
                iconResourceId = R.drawable.smog;
                break;
        }
        return iconResourceId;
    }
}
