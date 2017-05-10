package com.github.jupittar.thescreen.screen.moviedetails.cast;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieCastModule {

    private MovieCastContract.View mView;

    public MovieCastModule(MovieCastContract.View view) {
        mView = view;
    }

    @Provides
    public MovieCastContract.View provideView() {
        return mView;
    }

    @Provides
    @MovieCastScope
    public MovieCastContract.Presenter<MovieCastContract.View> providePresenter(
            MovieCastPresenter presenter
    ) {
        presenter.attach(mView);
        return presenter;
    }
}
