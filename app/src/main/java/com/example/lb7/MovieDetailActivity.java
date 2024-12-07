package com.example.lb7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView posterImageView;
    private TextView titleTextView, genreTextView, yearTextView, ratingTextView, linkTextView, nameRuTextView, webUrlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Инициализация элементов интерфейса
        posterImageView = findViewById(R.id.posterImageView);
        titleTextView = findViewById(R.id.titleTextView);
        genreTextView = findViewById(R.id.genreTextView);
        yearTextView = findViewById(R.id.yearTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        linkTextView = findViewById(R.id.linkTextView);
        nameRuTextView = findViewById(R.id.nameRuTextView); // Добавлен TextView для русского названия
        webUrlTextView = findViewById(R.id.webUrlTextView); // Добавлен TextView для веб-ссылки

        // Получение данных из Intent
        String title = getIntent().getStringExtra("movie_title");
        String genre = getIntent().getStringExtra("movie_genre");
        int year = getIntent().getIntExtra("movie_year", 0);
        float rating = getIntent().getFloatExtra("movie_rating", 0f);
        String posterUrl = getIntent().getStringExtra("movie_poster_url");
        final String movieLink = getIntent().getStringExtra("movie_link");
        String nameRu = getIntent().getStringExtra("movie_name_ru"); // Русское название
        final String webUrl = getIntent().getStringExtra("movie_web_url"); // Веб-ссылка

        // Установка данных в элементы интерфейса
        titleTextView.setText(title);
        genreTextView.setText(genre);
        yearTextView.setText("Год: " + year);
        ratingTextView.setText("Рейтинг: " + rating);
        nameRuTextView.setText("Русское название: " + nameRu); // Отображаем русское название
        webUrlTextView.setText("Ссылка: " + webUrl); // Отображаем веб-ссылку

        // Используем Glide для загрузки изображения постера
        Glide.with(this)
                .load(posterUrl)
                .into(posterImageView);

        // Клик по ссылке для открытия в браузере
        linkTextView.setOnClickListener(v -> {
            if (movieLink != null && !movieLink.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink));
                startActivity(browserIntent);
            } else {
                Toast.makeText(MovieDetailActivity.this, "Ссылка недоступна", Toast.LENGTH_SHORT).show();
            }
        });

        // Клик по веб-ссылке для открытия в браузере
        webUrlTextView.setOnClickListener(v -> {
            if (webUrl != null && !webUrl.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                startActivity(browserIntent);
            } else {
                Toast.makeText(MovieDetailActivity.this, "Ссылка недоступна", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
