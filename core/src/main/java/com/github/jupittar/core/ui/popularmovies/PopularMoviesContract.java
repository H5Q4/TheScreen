package com.github.jupittar.core.ui.popularmovies;

import com.github.jupittar.core.ui.base.BasePresenter;
import com.github.jupittar.core.ui.base.Contract;

public interface PopularMoviesContract {

  interface View extends Contract.View {

  }

  abstract class Presenter<V extends View> extends BasePresenter<V> {

  }

}
