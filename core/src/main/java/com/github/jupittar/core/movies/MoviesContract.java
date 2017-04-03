package com.github.jupittar.core.movies;

import com.github.jupittar.core.base.Contract;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.MoviesWrapper;
import com.github.jupittar.core.data.model.PagingInfo;

import java.util.List;

import io.reactivex.Maybe;

public interface MoviesContract {

    interface View extends Contract.View {
        void showMovies(List<Movie> movies);

        void showNoMoreMoviesFooter();

        boolean isMoviesEmpty();

        void showErrorLayout();

        void hideErrorLayout();

        void showReloadSnackbar();

        void updatePagingInfo(PagingInfo pagingInfo);

        void clearMovies();
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
        void showMovies(MovieTab tab, int page);
    }

    interface Interactor {
        Maybe<MoviesWrapper> getMovies(MovieTab tab, int page);
    }

}
