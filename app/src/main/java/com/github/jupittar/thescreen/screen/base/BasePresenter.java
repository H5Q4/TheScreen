package com.github.jupittar.thescreen.screen.base;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends Contract.View> implements Contract.Presenter<V> {

    private V mView;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attach(V view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
        clear();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void clear() {
        if (mCompositeDisposable == null) {
            return;
        }
        mCompositeDisposable.clear();
    }

    private boolean isViewAttached() {
        return mView != null;
    }

    protected V getMvpView() {
        if (!isViewAttached()) {
            throw new RuntimeException("Please call Presenter.attach(MvpView) before requesting" +
                    " data to the Presenter");
        }
        return mView;
    }

    //region Helper Methods
    protected boolean isNetworkException(Throwable t) {
        return (t instanceof ConnectException
                || t instanceof UnknownHostException
                || t instanceof SocketTimeoutException);
    }
    //endregion
}
