package com.example.lb7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // URL API
        String url = "https://kinopoiskapiunofficial.tech/api/v2.2/films/301";

        // Выполнение сетевого запроса в отдельном потоке
        new Thread(() -> {
            try {
                URL apiUrl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-API-KEY", "117aaf09-9b52-46a9-9b14-4675aa7e3257");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Movie movie = gson.fromJson(response.toString(), Movie.class);

                runOnUiThread(() -> {
                    if (movie != null) {
                        movieAdapter = new MovieAdapter(List.of(movie), this::openMovieDetails);
                        recyclerView.setAdapter(movieAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Ошибка в данных", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Ошибка запроса", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void openMovieDetails(Movie movie) {
        // Открытие экрана с деталями фильма
        Intent movieIntent = new Intent(MainActivity.this, MovieDetailActivity.class);

        // Передача данных в MovieDetailActivity
        movieIntent.putExtra("movie_title", movie.getTitle());
        movieIntent.putExtra("movie_genre", movie.getGenres().get(0).getGenre()); // Пример получения жанра
        movieIntent.putExtra("movie_year", movie.getYear());
        movieIntent.putExtra("movie_rating", movie.getRating());
        movieIntent.putExtra("movie_poster_url", movie.getPosterUrl());
        movieIntent.putExtra("movie_link", movie.getMovieLink());
        movieIntent.putExtra("movie_name_ru", movie.getNameRu()); // Передача русского названия
        movieIntent.putExtra("movie_web_url", movie.getWebUrl()); // Передача веб-ссылки

        startActivity(movieIntent);
    }
}
