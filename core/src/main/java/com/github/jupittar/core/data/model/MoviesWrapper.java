package com.github.jupittar.core.data.model;

import java.util.List;

public class MoviesWrapper {

    // region Fields
    private List<Movie> movies;
    private PagingInfo pagingInfo;
    // endregion

    // region Constructors
    public MoviesWrapper(List<Movie> movies, PagingInfo pagingInfo) {
        this.movies = movies;
        this.pagingInfo = pagingInfo;
    }
    // endregion

    // region Getters and Setters
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }
    // endregion

    // Helper Methods
    public boolean hasMovies() {
        return movies.size() > 0;
    }
    // endregion
}
