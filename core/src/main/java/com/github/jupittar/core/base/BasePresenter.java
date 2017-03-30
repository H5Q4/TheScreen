package com.github.jupittar.core.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends UiContract.View> implements UiContract.Presenter<V> {

    private V mView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attach(V view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
        unSubscribeAll();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unSubscribeAll() {
        if (mCompositeDisposable == null) {
            return;
        }
        mCompositeDisposable.dispose();
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public V getMvpView() {
        if (!isViewAttached()) {
            throw new RuntimeException("Please call Presenter.attach(MvpView) before requesting" +
                    " data to the Presenter");
        }
        return mView;
    }
}
