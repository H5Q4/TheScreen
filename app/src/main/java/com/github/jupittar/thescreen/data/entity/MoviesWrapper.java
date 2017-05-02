package com.github.jupittar.thescreen.data.entity;

import com.github.jupittar.thescreen.data.remote.response.Movie;

import java.util.List;

public class MoviesWrapper {

    // region Fields
    private List<Movie> movies;
    private PagingInfo pagingInfo;
    private long persistedTime;
    // endregion

    // region Constructors

    public MoviesWrapper(List<Movie> movies, PagingInfo pagingInfo, long persistedTime) {
        this.movies = movies;
        this.pagingInfo = pagingInfo;
        this.persistedTime = persistedTime;
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

    public long getPersistedTime() {
        return persistedTime;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }
    // endregion

    // Helper Methods
    public boolean hasMovies() {
        return movies.size() > 0;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >
                persistedTime + 24 * 60 * 60 * 1000;
    }
    // endregion
}
