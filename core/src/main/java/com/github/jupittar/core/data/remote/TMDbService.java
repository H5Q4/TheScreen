package com.github.jupittar.core.data.remote;

import com.github.jupittar.core.data.entity.Configuration;
import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.core.data.entity.ListResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbService {

  @GET("configuration")
  Single<Configuration> getApiConfiguration();

  @GET("movie/popular")
  Single<ListResponse<Movie>> getPopularMovies(@Query("page") int page);

  @GET("movie/now_playing")
  Single<ListResponse<Movie>> getNowPlayingMovies(@Query("page") int page);

  @GET("movie/top_rated")
  Single<ListResponse<Movie>> getTopRatedMovies(@Query("page") int page);

  @GET("movie/upcoming")
  Single<ListResponse<Movie>> getUpcomingMovies(@Query("page") int page);

  @GET("movie/{movie_id}")
  Single<Movie> getMovieDetails(@Path("movie_id") long movieId);

}
