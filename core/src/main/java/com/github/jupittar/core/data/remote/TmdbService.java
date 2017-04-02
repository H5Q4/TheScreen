package com.github.jupittar.core.data.remote;

import com.github.jupittar.core.data.model.Configuration;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.RawResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbService {

    @GET("configuration")
    Observable<Configuration> getApiConfiguration();

    @GET("movie/popular")
    Observable<RawResponse<Movie>> getPopularMovies(@Query("page") int page);

    @GET("movie/now_playing")
    Observable<RawResponse<Movie>> getNowPlayingMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Observable<RawResponse<Movie>> getTopRatedMovies(@Query("page") int page);

    @GET("movie/upcoming")
    Observable<RawResponse<Movie>> getUpcomingMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") long movieId);

}
