package com.github.jupittar.thescreen.screen.moviedetails.info;

import android.text.TextUtils;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.thescreen.data.remote.response.Company;
import com.github.jupittar.thescreen.data.remote.response.CrewBean;
import com.github.jupittar.thescreen.data.remote.response.Genres;
import com.github.jupittar.thescreen.data.remote.response.Movie;
import com.github.jupittar.thescreen.data.remote.response.SpokenLanguages;
import com.github.jupittar.thescreen.helper.SchedulerProvider;
import com.github.jupittar.thescreen.screen.base.BasePresenter;
import com.github.jupittar.thescreen.util.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MovieInfoPresenter
        extends BasePresenter<MovieInfoContract.View>
        implements MovieInfoContract.Presenter<MovieInfoContract.View> {

    private MovieInfoContract.Interactor mInteractor;
    private SchedulerProvider mSchedulerProvider;

    @Inject
    public MovieInfoPresenter(MovieInfoContract.Interactor interactor,
                              SchedulerProvider schedulerProvider) {
        mInteractor = interactor;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void showMovieInfo(long movieId) {
        Disposable disposable = mInteractor
                .getMovieDetails(movieId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(d -> getMvpView().showLoading())
                .doOnTerminate(() -> {
                    getMvpView().hideLoading();
                    getMvpView().showContentView();
                })
                .subscribe(movieDetailsWrapper -> {
                    if (movieDetailsWrapper != null) {
                        Movie movie = movieDetailsWrapper.getMovie();
                        getMvpView().showRating(movie.getVoteAverage());
                        getMvpView().showDuration(movie.getRuntime());
                        getMvpView().showOverviewText(movie.getOverview());
                        getMvpView().showBudgetText(movie.getBudget());
                        getMvpView().showRevenueText(movie.getRevenue());
                        List<String> genres = Stream.of(movie.getGenres())
                                .map(Genres::getName)
                                .collect(Collectors.toList());
                        getMvpView().showGenreText(TextUtils.join(", ", genres));
                        List<String> companies = Stream.of(movie.getProductionCompanies())
                                .map(Company::getName)
                                .collect(Collectors.toList());
                        getMvpView().showCompanyText(TextUtils.join(", ", companies));
                        List<String> languages = Stream.of(movie.getSpokenLanguages())
                                .map(SpokenLanguages::getName)
                                .collect(Collectors.toList());
                        getMvpView().showLanguageText(TextUtils.join(", ", languages));
                        List<CrewBean> crews = movieDetailsWrapper.getCredits().getCrew();
                        List<String> directors = Stream.of(crews)
                                .filter(crew -> crew.getJob().equalsIgnoreCase("Director"))
                                .map(CrewBean::getName)
                                .collect(Collectors.toList());
                        getMvpView().showDirectorText(TextUtils.join(", ", directors));
                        getMvpView().showSimilarMovies(movieDetailsWrapper.getSimilarMovies());
                        RxBus.getDefault().publishSticky(Constants.EVENT_TAG_CAST_LOADED,
                                movieDetailsWrapper.getCredits().getCast());
                    }
                }, throwable -> getMvpView().showErrorMessage(throwable));
        addDisposable(disposable);
    }
}
