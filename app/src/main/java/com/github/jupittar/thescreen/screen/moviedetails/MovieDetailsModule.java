package com.github.jupittar.thescreen.screen.moviedetails;


import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsModule {

    private MovieDetailsContract.View mView;

    public MovieDetailsModule(MovieDetailsContract.View view) {
        mView = view;
    }

    @Provides
    public MovieDetailsContract.View provideView() {
        return mView;
    }

    @Provides
    @MovieDetailsScope
    public MovieDetailsContract.Presenter<MovieDetailsContract.View> providePresenter(
            MovieDetailsPresenter presenter) {
        presenter.attach(mView);
        return presenter;
    }

    @Provides
    @MovieDetailsScope
    public MovieDetailsContract.Interactor provideInteractor(MovieDetailsInteractor interactor) {
        return interactor;
    }

}
