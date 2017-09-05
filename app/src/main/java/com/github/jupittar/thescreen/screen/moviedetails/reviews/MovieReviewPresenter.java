package com.github.jupittar.thescreen.screen.moviedetails.reviews;


import com.github.jupittar.thescreen.helper.SchedulerProvider;
import com.github.jupittar.thescreen.screen.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MovieReviewPresenter
        extends BasePresenter<MovieReviewContract.View>
        implements MovieReviewContract.Presenter<MovieReviewContract.View> {

    private MovieReviewContract.interactor mInteractor;
    private SchedulerProvider mSchedulerProvider;

    @Inject
    public MovieReviewPresenter(MovieReviewContract.interactor interactor,
                                SchedulerProvider schedulerProvider) {
        mInteractor = interactor;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void showReviews(long movieId) {
        getMvpView().showLoading();
        final Disposable disposable = mInteractor.getReviews(movieId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnTerminate(() -> getMvpView().hideLoading())
                .subscribe(reviewWrapper -> {
                    getMvpView().showReviews(reviewWrapper.getReviews());
                }, throwable -> {
                    getMvpView().showErrorMessage(throwable);
                });
        addDisposable(disposable);
    }
}
