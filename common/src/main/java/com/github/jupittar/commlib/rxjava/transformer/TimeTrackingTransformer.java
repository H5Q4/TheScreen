package com.github.jupittar.commlib.rxjava.transformer;

import android.util.Pair;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;

/**
 * This class can be the tracking of the emission time of the items produced by the Observable.
 * In other words, how much time has passed since the start when this item was emitted.
 *
 * @param <R>
 */
public class TimeTrackingTransformer<R> implements ObservableTransformer<R, R> {

    private Consumer<Long> mTimeConsumer;

    public TimeTrackingTransformer(Consumer<Long> mTimeConsumer) {
        this.mTimeConsumer = mTimeConsumer;
    }

    public static <R> TimeTrackingTransformer<R> timeTracker(Consumer<Long> timeConsumer) {
        return new TimeTrackingTransformer<>(timeConsumer);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return Observable
                .combineLatest(
                        Observable.just(new Date()),
                        upstream,
                        Pair::create
                )
                .doOnNext(pair -> {
                    Date now = new Date();
                    long diff = now.getTime() - pair.first.getTime();
                    long diffSeconds = diff / 1000;
                    mTimeConsumer.accept(diffSeconds);
                })
                .map(pair -> pair.second);
    }
}
