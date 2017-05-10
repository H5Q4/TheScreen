package com.github.jupittar.thescreen.screen.moviedetails.cast;

import com.github.jupittar.thescreen.data.remote.response.CastBean;
import com.github.jupittar.thescreen.screen.base.Contract;

import java.util.List;

public interface MovieCastContract {

    interface View extends Contract.View {

        void showCast(List<CastBean> cast);
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
        void showCast();
    }

    interface Interactor {
    }

}
