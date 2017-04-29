package com.github.jupittar.core.moviedetails.info;

import com.github.jupittar.core.base.Contract;

public interface MovieInfoContract {

    interface View extends Contract.View {
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
    }

    interface Interactor {
    }

}
