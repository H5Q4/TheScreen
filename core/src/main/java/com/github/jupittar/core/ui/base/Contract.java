package com.github.jupittar.core.ui.base;


import io.reactivex.disposables.Disposable;

public interface Contract {

  interface View {
    void showLoading();
    void hideLoading();
    void showErrorMessage();
  }

  interface Presenter<V extends View> {
    void attach(V view);
    void detach();
    void addDisposable(Disposable disposable);
    void unSubscribeAll();
  }

}
