package ru.vsu.cs.weatherapp2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import ru.vsu.cs.weatherapp2.db.DatabaseHelper;
import ru.vsu.cs.weatherapp2.service.User;
import ru.vsu.cs.weatherapp2.service.UserAdapter;

public class UserActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ImageView buttonBack;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> cityWeatherList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityWeatherList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        cityWeatherList = databaseHelper.getAllCityWeather();

        adapter = new UserAdapter(cityWeatherList);
        recyclerView.setAdapter(adapter);

        buttonBack = findViewById(R.id.button_Back);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        userCursor.close();
//        db.close();
    }
}
