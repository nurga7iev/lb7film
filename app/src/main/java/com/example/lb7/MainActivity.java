package com.example.lb7;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private EditText searchInput;

    private List<Movie> movieList = new ArrayList<>();
    private List<Movie> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchInput = findViewById(R.id.searchInput);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Список ID фильмов
        List<Integer> movieIds = Arrays.asList(301, 302, 303, 304, 305,306,307,308,309,310,311,312,313,314,315,316,317,318,320); // Пример ID фильмов

        // Выполнение запросов в отдельном потоке
        new Thread(() -> {
            for (int id : movieIds) {
                fetchMovieData(id);
            }
        }).start();

        // Обработчик для поиска
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (query.isEmpty()) {
                    filteredList.clear();
                    filteredList.addAll(movieList); // Отображение всех фильмов
                } else {
                    filterMovies(query);
                }
                updateRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchMovieData(int id) {
        try {
            String url = "https://kinopoiskapiunofficial.tech/api/v2.2/films/" + id;
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

            // Обновляем список фильмов
            runOnUiThread(() -> {
                if (movie != null) {
                    movieList.add(movie);
                    filteredList.add(movie);
                    updateRecyclerView();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Ошибка запроса для ID: " + id, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void filterMovies(String query) {
        filteredList.clear();
        for (Movie movie : movieList) {
            if (movie != null && movie.getNameRu() != null) {
                String title = movie.getNameRu().toLowerCase();
                if (title.contains(query.toLowerCase())) {
                    filteredList.add(movie);
                }
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Фильм не найден", Toast.LENGTH_SHORT).show();
        }
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(filteredList, this::openMovieDetails);
            recyclerView.setAdapter(movieAdapter);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void openMovieDetails(Movie movie) {
        Intent movieIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
        movieIntent.putExtra("movie_title", movie.getNameRu());
        movieIntent.putExtra("movie_genre", movie.getGenres().get(0).getGenre());
        movieIntent.putExtra("movie_year", movie.getYear());
        movieIntent.putExtra("movie_rating", movie.getRatingKinopoisk()); // рейтинг Kinopoisk
        movieIntent.putExtra("movie_poster_url", movie.getPosterUrl());
        movieIntent.putExtra("movie_link", movie.getWebUrl());
        startActivity(movieIntent);
    }
}
