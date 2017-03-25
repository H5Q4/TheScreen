package com.github.jupittar.core.ui.movies;

import com.github.jupittar.core.ui.base.BasePresenter;
import com.github.jupittar.core.ui.base.Contract;

public interface MoviesContract {

  interface View extends Contract.View {

  }

  abstract class Presenter<V extends View> extends BasePresenter<V> {

  }

}
