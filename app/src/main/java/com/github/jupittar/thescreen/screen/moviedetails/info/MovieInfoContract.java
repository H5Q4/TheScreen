package com.github.jupittar.thescreen.screen.moviedetails.info;

import com.github.jupittar.thescreen.screen.base.Contract;

public interface MovieInfoContract {

    interface View extends Contract.View {
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
    }

    interface Interactor {
    }

}
