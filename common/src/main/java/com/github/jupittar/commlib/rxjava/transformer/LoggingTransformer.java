package com.github.jupittar.commlib.rxjava.transformer;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * This class can be used to print additional logging during the execution of the Observable.
 *
 * @param <R>
 */
public class LoggingTransformer<R> implements ObservableTransformer<R, R> {

    private final String tag;

    public LoggingTransformer(String tag) {
        this.tag = tag;
    }

    public static <R> LoggingTransformer<R> debugLog(String tag) {
        return new LoggingTransformer<>(tag);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return upstream
                .doOnNext(v -> this.log("doOnNext", v))
                .doOnError(error -> this.log("doOnError", error))
                .doOnComplete(() -> this.log("doOnComplete", upstream.toString()))
                .doOnTerminate(() -> this.log("doOnTerminate", upstream.toString()))
                .doOnDispose(() -> this.log("doOnDispose", upstream.toString()))
                .doOnSubscribe(v -> this.log("doOnSubscribe", upstream.toString()));
    }

    private void log(String stage, Object item) {
        Log.d("Observable-DEBUG:" + tag, stage + ":" + Thread.currentThread().getName() + ":" + item);
    }

    private void log(String stage, Throwable throwable) {
        Log.e("Observable-DEBUG:" + tag, stage + ":" + Thread.currentThread().getName() + ": error", throwable);
    }

}
