package com.github.jupittar.core.moviedetails;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.github.jupittar.core.base.BasePresenter;
import com.github.jupittar.core.data.model.Images;
import com.github.jupittar.core.helper.SchedulerHelper;
import com.github.jupittar.core.util.Constants;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MovieDetailsPresenter
        extends BasePresenter<MovieDetailsContract.View>
        implements MovieDetailsContract.Presenter<MovieDetailsContract.View> {

    private MovieDetailsContract.Interactor mInteractor;
    private SchedulerHelper mSchedulerHelper;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.Interactor interactor,
                                 SchedulerHelper schedulerHelper) {
        mInteractor = interactor;
        mSchedulerHelper = schedulerHelper;
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
                                    .collect(Collectors.toList());
                        }
                    }
                    return Collections.<String>emptyList();
                })
                .subscribeOn(mSchedulerHelper.backgroundThread())
                .observeOn(mSchedulerHelper.mainThread())
                .subscribe(urls -> {
                    getMvpView().showImages(urls);
                }, throwable -> {
                    getMvpView().showErrorBackdrop();
                });
        addDisposable(disposable);
    }
}
