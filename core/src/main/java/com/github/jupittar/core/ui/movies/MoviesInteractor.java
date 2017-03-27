package com.github.jupittar.core.ui.movies;

import com.github.jupittar.core.data.entity.ListResponse;
import com.github.jupittar.core.data.entity.Movie;
import com.github.jupittar.core.data.remote.TMDbService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesInteractor {

  private TMDbService mTMDbService;

  @Inject
  public MoviesInteractor(TMDbService TMDbService) {
    mTMDbService = TMDbService;
  }

  public Single<List<Movie>> loadNowPlayingMovies(int page) {
    return mTMDbService
            .getNowPlayingMovies(page)
            .map(ListResponse::getResults);
  }

}
