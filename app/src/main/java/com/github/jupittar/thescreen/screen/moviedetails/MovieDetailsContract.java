package com.github.jupittar.thescreen.screen.moviedetails;

import com.github.jupittar.thescreen.data.entity.ImagesWrapper;
import com.github.jupittar.thescreen.screen.base.Contract;

import java.util.List;

import io.reactivex.Observable;

public interface MovieDetailsContract {

    interface View extends Contract.View {
        void showImages(List<String> urls);
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
        void showImages(long movieId);
    }

    interface Interactor {
        Observable<ImagesWrapper> getImages(long movieId);
    }

}
