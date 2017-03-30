package com.github.jupittar.core.movies;

import com.github.jupittar.core.base.BasePresenter;
import com.github.jupittar.core.data.model.RawResponse;
import com.github.jupittar.core.helper.SchedulerHelper;

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
        getMvpView().showLoading();
        Disposable disposable = mInteractor
                .loadMovies(tab, page)
                .map(RawResponse::getResults)
                .subscribeOn(mSchedulerHelper.backgroundThread())
                .observeOn(mSchedulerHelper.mainThread())
                .subscribe(movies -> {
                    getMvpView().showMovies(movies);
                    getMvpView().hideLoading();
                }, throwable -> {
                    getMvpView().hideLoading();
                    getMvpView().showErrorMessage();
                });
        addDisposable(disposable);
    }
}