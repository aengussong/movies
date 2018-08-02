package com.aengussong.movies.network;

import com.aengussong.movies.network.response.MoviesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiLinks {

    @GET("/movie/top_rated")
    Single<MoviesResponse> getMovies(@Query("page") int page);
}
