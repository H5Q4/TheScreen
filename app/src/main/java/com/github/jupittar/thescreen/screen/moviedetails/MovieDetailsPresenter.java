package com.github.jupittar.thescreen.screen.moviedetails;

import com.annimon.stream.Stream;
import com.github.jupittar.thescreen.data.model.Images;
import com.github.jupittar.thescreen.screen.base.BasePresenter;
import com.github.jupittar.thescreen.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsPresenter
        extends BasePresenter<MovieDetailsContract.View>
        implements MovieDetailsContract.Presenter<MovieDetailsContract.View> {

    private MovieDetailsContract.Interactor mInteractor;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.Interactor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void showImages(long movieId) {
        Disposable disposable = mInteractor
                .getImages(movieId)
                .filter(imagesWrapper -> imagesWrapper != null)
                .map(imagesWrapper -> {
                    Images images = imagesWrapper.getImages();
                    if (images != null) {
                        List<Images.BackdropsBean> backdrops = images.getBackdrops();
                        if (backdrops != null) {
                            return Stream.of(backdrops)
                                    .map(backdropsBean -> String.format("%s%s%s",
                                            Constants.IMAGE_BASE_URL,
                                            Constants.IMAGE_SIZE_W780,
                                            backdropsBean.getFilePath()))
                                    .collect(ArrayList<String>::new, (list, url) -> {
                                        list.add(0, url);
                                    });
                        }
                    }
                    return Collections.<String>emptyList();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> getMvpView().showLoading())
                .doFinally(() -> getMvpView().hideLoading())
                .subscribe(urls -> {
                    getMvpView().showImages(urls);
                }, throwable -> {
                    getMvpView().showErrorMessage(throwable);
                });
        addDisposable(disposable);
    }
}
