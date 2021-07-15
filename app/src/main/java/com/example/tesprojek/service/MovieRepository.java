package com.example.tesprojek.service;

import com.example.tesprojek.model.movie.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieRepository {
    @GET("3/movie/popular?api_key=b450a53c85618a1de3753009dd804d00")
    Call<MovieResponse> getMovie();
}

