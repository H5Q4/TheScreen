package com.github.jupittar.thescreen.screen.moviedetails.reviews;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieReviewModule {

    private MovieReviewContract.View mView;

    public MovieReviewModule(MovieReviewContract.View view) {
        mView = view;
    }

    @Provides
    public MovieReviewContract.View provideView() {
        return mView;
    }

    @Provides
    @MovieReviewScope
    public MovieReviewContract.Presenter<MovieReviewContract.View> providePresenter(
            MovieReviewPresenter presenter
    ) {
        presenter.attach(mView);
        return presenter;
    }

    @Provides
    @MovieReviewScope
    public MovieReviewContract.interactor provideInteractor(MovieReviewInteractor interactor) {
        return interactor;
    }
}
