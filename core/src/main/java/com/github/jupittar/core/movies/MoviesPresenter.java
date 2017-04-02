package com.github.jupittar.core.movies;

import com.github.jupittar.core.base.BasePresenter;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.PagingInfo;
import com.github.jupittar.core.helper.SchedulerHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MoviesPresenter
        extends BasePresenter<MoviesUiContract.View>
        implements MoviesUiContract.Presenter<MoviesUiContract.View> {

    private MoviesInteractor mInteractor;
    private SchedulerHelper mSchedulerHelper;

    @Inject
    public MoviesPresenter(MoviesInteractor interactor, SchedulerHelper schedulerHelper) {
        mInteractor = interactor;
        mSchedulerHelper = schedulerHelper;
    }

    @Override
    public void showMovies(MovieTab tab, int page) {
        if (page == PagingInfo.NO_MORE_PAGES) return;

        if (page == 1) {
            getMvpView().showLoading();
            getMvpView().clearMovies();
        }

        Disposable disposable = mInteractor
                .loadMovies(tab, page)
                .subscribeOn(mSchedulerHelper.backgroundThread())
                .observeOn(mSchedulerHelper.mainThread())
                .subscribe(moviesWrapper -> {
                    List<Movie> movies = moviesWrapper.getMovies();
                    PagingInfo pagingInfo = moviesWrapper.getPagingInfo();
                    getMvpView().updatePagingInfo(pagingInfo);

                    if (pagingInfo.isLastPage()) getMvpView().addNoMoreMoviesFooter();

                    getMvpView().showMovies(movies);
                    getMvpView().hideLoading();
                }, throwable -> {
                    if (isNetworkException(throwable)) {
                        if (getMvpView().isMoviesEmpty()) {
                            getMvpView().showErrorLayout();
                        } else if (page > 1) {
                            getMvpView().showReloadSnackbar();
                        }
                    } else {
                        getMvpView().showErrorMessage();
                    }
                    throwable.printStackTrace();
                    getMvpView().hideLoading();
                });
        addDisposable(disposable);
    }
}
