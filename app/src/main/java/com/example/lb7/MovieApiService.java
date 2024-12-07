package com.example.lb7;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface MovieApiService {
    @GET("movies") // Replace with the actual API endpoint
    Call<List<Movie>> getMovies();
}
