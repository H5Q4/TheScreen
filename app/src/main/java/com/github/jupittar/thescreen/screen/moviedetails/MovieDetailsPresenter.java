package com.github.jupittar.thescreen.screen.moviedetails;

import com.annimon.stream.Stream;
import com.github.jupittar.thescreen.data.remote.response.Images;
import com.github.jupittar.thescreen.helper.SchedulerProvider;
import com.github.jupittar.thescreen.screen.base.BasePresenter;
import com.github.jupittar.thescreen.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MovieDetailsPresenter
        extends BasePresenter<MovieDetailsContract.View>
        implements MovieDetailsContract.Presenter<MovieDetailsContract.View> {

    private MovieDetailsContract.Interactor mInteractor;
    private SchedulerProvider mSchedulerProvider;

    @Inject
    public MovieDetailsPresenter(MovieDetailsContract.Interactor interactor,
                                 SchedulerProvider schedulerProvider) {
        mInteractor = interactor;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void showImages(long movieId, String defaultUrl) {
        Disposable disposable = mInteractor
                .getImages(movieId)
                .filter(imagesWrapper -> imagesWrapper != null)
                .map(imagesWrapper -> {
                    Images images = imagesWrapper.getImages();
                    if (images != null) {
                        List<Images.BackdropsBean> backdrops = images.getBackdrops();
                        if (backdrops != null && backdrops.size() != 0) {
                            return Stream.of(backdrops)
                                    .map(backdropsBean -> concatPosterUrl(backdropsBean.getFilePath()))
                                    .collect(ArrayList<String>::new, (list, url) -> {
                                        list.add(0, url);
                                    });
                        }
                        return Collections.singletonList(concatPosterUrl(defaultUrl));
                    }
                    return Collections.singletonList(concatPosterUrl(defaultUrl));
                })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnSubscribe(d -> getMvpView().showLoading())
                .doOnTerminate(() -> getMvpView().hideLoading())
                .subscribe(urls -> {
                    getMvpView().showImages(urls);
                }, throwable -> {
                    getMvpView().showErrorMessage(throwable);
                });
        addDisposable(disposable);
    }

    private String concatPosterUrl(String url) {
        return String.format("%s%s%s",
                Constants.IMAGE_BASE_URL,
                Constants.IMAGE_SIZE_W780,
                url);
    }
}
