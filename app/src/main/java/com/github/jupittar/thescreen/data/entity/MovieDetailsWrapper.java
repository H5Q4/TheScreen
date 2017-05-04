package com.github.jupittar.thescreen.data.entity;


import com.github.jupittar.thescreen.data.remote.response.Credits;
import com.github.jupittar.thescreen.data.remote.response.Movie;

import java.util.List;

public class MovieDetailsWrapper {

    //region Fields
    private Movie movie;
    private Credits credits;
    private List<Movie> similarMovies;
    //endregion


    //region Constructors
    public MovieDetailsWrapper(Movie movie, Credits credits, List<Movie> similarMovies) {
        this.movie = movie;
        this.credits = credits;
        this.similarMovies = similarMovies;
    }
    //endregion

    //region Getters and Setters
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public List<Movie> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(List<Movie> similarMovies) {
        this.similarMovies = similarMovies;
    }
    //endregion
}
