package com.github.jupittar.core.data.remote;

import com.github.jupittar.core.data.model.Configuration;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.RawResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbService {

  @GET("configuration")
  Single<Configuration> getApiConfiguration();

  @GET("movie/popular")
  Single<RawResponse<Movie>> getPopularMovies(@Query("page") int page);

  @GET("movie/now_playing")
  Single<RawResponse<Movie>> getNowPlayingMovies(@Query("page") int page);

  @GET("movie/top_rated")
  Single<RawResponse<Movie>> getTopRatedMovies(@Query("page") int page);

  @GET("movie/upcoming")
  Single<RawResponse<Movie>> getUpcomingMovies(@Query("page") int page);

  @GET("movie/{movie_id}")
  Single<Movie> getMovieDetails(@Path("movie_id") long movieId);

}
