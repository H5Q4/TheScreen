package com.github.jupittar.thescreen.screen.movies;

import com.github.jupittar.thescreen.data.entity.PagingInfo;
import com.github.jupittar.thescreen.data.remote.response.Movie;
import com.github.jupittar.thescreen.helper.SchedulerProvider;
import com.github.jupittar.thescreen.screen.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MoviesPresenter
        extends BasePresenter<MoviesContract.View>
        implements MoviesContract.Presenter<MoviesContract.View> {

    private MoviesContract.Interactor mInteractor;
    private SchedulerProvider mSchedulerProvider;

    @Inject
    public MoviesPresenter(MoviesContract.Interactor interactor,
                           SchedulerProvider schedulerProvider) {
        mInteractor = interactor;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void showMovies(MovieTab tab, int page) {
        if (page == PagingInfo.NO_MORE_PAGES) return;

        if (page == 1) {
            getMvpView().hideErrorLayout();
            getMvpView().showLoading();
            getMvpView().clearMovies();
        }

        Disposable disposable = mInteractor
                .getMovies(tab, page)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnTerminate(() -> getMvpView().hideLoading())
                .subscribe(moviesWrapper -> {
                    List<Movie> movies = moviesWrapper.getMovies();
                    PagingInfo pagingInfo = moviesWrapper.getPagingInfo();
                    getMvpView().updatePagingInfo(pagingInfo);

                    getMvpView().showMovies(movies);

                    if (pagingInfo.getCurrentPage() == 1) {
                        getMvpView().addLoadingFooter();
                    }

                    if (pagingInfo.isLastPage()) {
                        getMvpView().showNoMoreMoviesFooter();
                    }
                }, throwable -> {
                    if (isNetworkException(throwable)) {
                        if (getMvpView().isMoviesEmpty()) {
                            getMvpView().showErrorLayout();
                        } else if (page > 1) {
                            getMvpView().showReloadSnackbar();
                        }
                    } else {
                        getMvpView().showErrorMessage(throwable);
                    }
                    throwable.printStackTrace();
                });
        addDisposable(disposable);
    }
}
