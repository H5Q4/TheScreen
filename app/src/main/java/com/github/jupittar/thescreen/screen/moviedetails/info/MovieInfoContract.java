package com.github.jupittar.thescreen.screen.moviedetails.info;

import com.github.jupittar.thescreen.data.entity.MovieDetailsWrapper;
import com.github.jupittar.thescreen.data.remote.response.Movie;
import com.github.jupittar.thescreen.screen.base.Contract;

import java.util.List;

import io.reactivex.Observable;

public interface MovieInfoContract {

    interface View extends Contract.View {
        void showOverviewText(String overview);

        void showRevenueText(int revenue);

        void showBudgetText(int budget);

        void showLanguageText(String languages);

        void showCompanyText(String companies);

        void showDirectorText(String director);

        void showSimilarMovies(List<Movie> similarMovies);

        void showRating(double voteAverage);

        void showDuration(int runtime);

        void showGenreText(String s);
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
        void showMovieInfo(long movieId);
    }

    interface Interactor {
        Observable<MovieDetailsWrapper> getMovieDetails(long movieId);
    }

}
