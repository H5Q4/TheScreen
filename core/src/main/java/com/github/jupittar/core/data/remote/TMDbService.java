package com.github.jupittar.core.data.remote;

import com.github.jupittar.core.data.entity.Configuration;
import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.core.data.entity.RawResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbService {

  @GET("configuration")
  Single<Configuration> getApiConfiguration();

  @GET("movie/popular")
  Single<RawResponse<Movie>> getPopularMovies(@Query("page") int page);

  @GET("movie/{movieId}")
  Single<Movie> getMovieDetails(@Path("movieId") long movieId);

}
