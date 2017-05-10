package com.github.jupittar.thescreen.screen.moviedetails.cast;

import com.github.jupittar.commlib.rxbus.RxBus;
import com.github.jupittar.thescreen.data.remote.response.CastBean;
import com.github.jupittar.thescreen.screen.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import static com.github.jupittar.thescreen.util.Constants.EVENT_TAG_CAST_LOADED;


public class MovieCastPresenter
        extends BasePresenter<MovieCastContract.View>
        implements MovieCastContract.Presenter<MovieCastContract.View> {

    @Inject
    public MovieCastPresenter() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void showCast() {
        RxBus.getDefault().subscribeSticky(EVENT_TAG_CAST_LOADED, getMvpView(), busEvent -> {
            getMvpView().showCast((List<CastBean>) busEvent.getData());
        });
    }

    @Override
    public void detach() {
        RxBus.getDefault().unregister(getMvpView());
        super.detach();
    }
}
