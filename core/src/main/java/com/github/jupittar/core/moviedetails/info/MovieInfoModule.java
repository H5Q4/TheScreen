package com.github.jupittar.core.moviedetails.info;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieInfoModule {

    private MovieInfoContract.View mView;

    public MovieInfoModule(MovieInfoContract.View view) {
        mView = view;
    }

    @Provides
    public MovieInfoContract.View provideView() {
        return mView;
    }

    @Provides
    @MovieInfoScope
    public MovieInfoContract.Presenter<MovieInfoContract.View> providePresenter(
            MovieInfoPresenter presenter
    ) {
        presenter.attach(mView);
        return presenter;
    }

    @Provides
    @MovieInfoScope
    public MovieInfoContract.Interactor provideInteractor(MovieInfoInteractor interactor) {
        return interactor;
    }
}
