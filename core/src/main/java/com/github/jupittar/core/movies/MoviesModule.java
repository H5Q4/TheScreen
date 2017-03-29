package com.github.jupittar.core.movies;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

  private MoviesUiContract.View mView;

  public MoviesModule(MoviesUiContract.View view) {
    mView = view;
  }

  @Provides
  public MoviesUiContract.View provideView() {
    return mView;
  }

  @Provides
  @MoviesScope
  public MoviesUiContract.Presenter<MoviesUiContract.View> providePresenter(
      MoviesPresenter presenter
  ) {
    presenter.attach(mView);
    return presenter;
  }

}
