package ru.vsu.cs.weatherapp2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vsu.cs.weatherapp2.Entity.AllWeather;
import ru.vsu.cs.weatherapp2.Entity.WeatherInfo;
import ru.vsu.cs.weatherapp2.db.DatabaseHelper;
import ru.vsu.cs.weatherapp2.service.WeatherService;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private WeatherService service;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        databaseHelper = new DatabaseHelper(getApplicationContext());
//        databaseHelper.create_db();
//        db = databaseHelper.open();
        service = new WeatherService();


        findViewById(R.id.infoButton).setOnClickListener(e -> {
            getWeatherInfo();

        });

        findViewById(R.id.historyButton).setOnClickListener(e -> {
            toHistory();
        });
    }

    public void getWeatherInfo() {
        EditText text = findViewById(R.id.placeName);
        String placeName = text.getText().toString();

        service.getInfo(placeName).enqueue(new Callback<AllWeather>() {
            @Override
            public void onResponse(Call<AllWeather> call, Response<AllWeather> response) {

                if (response.isSuccessful()) {
                    EditText text = findViewById(R.id.placeName);
                    AllWeather weather = response.body();

                    if (weather != null) {
                        WeatherInfo info = createWeatherInfo(
                                weather.getMain().getTemp(),
                                weather.getMain().getTempMax(),
                                weather.getMain().getTempMin(),
                                weather.getMain().getFeelsLike(),
                                weather.getMain().getPressure(),
                                weather.getWeather().get(0).getDescription(),
                                text.getText().toString()
                        );
                        setDataInTextEdit(info);
                        save(info);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllWeather> call, Throwable t) {
                t.fillInStackTrace();
            }
        });
    }

    private WeatherInfo createWeatherInfo(
            double temp, double max, double min,
            double fell, double pressure, String description, String place
    ) {
        return new WeatherInfo(temp, max, min, fell, pressure, description, place);
    }

    private void setDataInTextEdit(WeatherInfo info) {
        TextView e = findViewById(R.id.temperature);
        e.setText(info.getTemperature());
        e = findViewById(R.id.max);
        e.setText(info.getMaxT());
        e = findViewById(R.id.min);
        e.setText(info.getMinT());
        e = findViewById(R.id.fill_like);
        e.setText(info.getFillLike());
        e = findViewById(R.id.pressure);
        e.setText(info.getPressure());
        e = findViewById(R.id.description);
        e.setText(String.valueOf(info.getWeather()));
    }

    public void toHistory() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @SuppressLint("SimpleDateFormat")
    public void save(WeatherInfo info) {
        ContentValues cv = new ContentValues();
        Date date = new Date();
//
//        cv.put(DatabaseHelper.COLUMN_TEMPERATURE, info.getTemperature());
//        cv.put(DatabaseHelper.COLUMN_MAX, info.getMaxT());
//        cv.put(DatabaseHelper.COLUMN_MIN, info.getMinT());
//        cv.put(DatabaseHelper.COLUMN_FILL_LIKE, info.getFillLike());
//        cv.put(DatabaseHelper.COLUMN_PRESSURE, info.getPressure());
//        cv.put(DatabaseHelper.COLUMN_WEATHER_DESCRIPTION, info.getWeather());
//        cv.put(DatabaseHelper.COLUMN_PLACE, info.getPlace());
//        cv.put(DatabaseHelper.DATE, new SimpleDateFormat().format(date.getTime()).split(" ")[0]);
//
//        db.insert(DatabaseHelper.TABLE, null, cv);

        // goHome();
    }

    private void goHome() {
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
