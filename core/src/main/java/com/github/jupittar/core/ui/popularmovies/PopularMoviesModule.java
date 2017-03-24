package com.github.jupittar.core.ui.popularmovies;

import dagger.Module;
import dagger.Provides;

@Module
public class PopularMoviesModule {

  private PopularMoviesContract.View mView;

  public PopularMoviesModule(PopularMoviesContract.View view) {
    mView = view;
  }

  @Provides
  public PopularMoviesContract.View provideView() {
    return mView;
  }

  @Provides
  @PopularMoviesScope
  public PopularMoviesContract.Presenter<PopularMoviesContract.View> providePresenter(
      PopularMoviesPresenter presenter
  ) {
    presenter.attach(mView);
    return presenter;
  }

  @Provides
  @PopularMoviesScope
  public PopularMoviesInteractor provideInteractor(PopularMoviesInteractor interactor) {
    return interactor;
  }

}
