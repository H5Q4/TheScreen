package com.github.jupittar.thescreen.screen.movies;

import com.github.jupittar.thescreen.data.model.Movie;
import com.github.jupittar.thescreen.data.model.MoviesWrapper;
import com.github.jupittar.thescreen.data.model.PagingInfo;
import com.github.jupittar.thescreen.screen.base.Contract;

import java.util.List;

import io.reactivex.Observable;

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

        void addLoadingFooter();
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
        void showMovies(MovieTab tab, int page);
    }

    interface Interactor {
        Observable<MoviesWrapper> getMovies(MovieTab tab, int page);
    }

}
