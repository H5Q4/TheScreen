package com.github.jupittar.core.moviedetails;

import com.github.jupittar.core.base.Contract;
import com.github.jupittar.core.data.model.Images;

import java.util.List;

import io.reactivex.Maybe;

public interface MovieDetailsContract {

    interface View extends Contract.View {
        void showImages(List<String> urls);
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {
        void showImages(long movieId);
    }

    interface Interactor {
        Maybe<Images> getImages(long movieId);
    }

}
