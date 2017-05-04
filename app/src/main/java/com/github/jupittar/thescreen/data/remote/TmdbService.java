package com.github.jupittar.thescreen.data.remote;

import com.github.jupittar.thescreen.data.entity.RawResponse;
import com.github.jupittar.thescreen.data.remote.response.Credits;
import com.github.jupittar.thescreen.data.remote.response.Images;
import com.github.jupittar.thescreen.data.remote.response.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbService {

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

    @GET("movie/{movie_id}/images")
    Observable<Images> getMovieImages(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/credits")
    Observable<Credits> getMovieCredits(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/similar")
    Observable<RawResponse<Movie>> getSimilarMovies(@Path("movie_id") long movieId);


}
