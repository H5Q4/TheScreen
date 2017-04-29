package com.github.jupittar.commlib.rxbus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * A simple event bus implemented by RxJava
 */
public class RxBus {

    private Map<String, PublishSubject<BusEvent>> mSubjectMap = new HashMap<>();
    private Map<Object, CompositeDisposable> mCompositeDisposableMap = new HashMap<>();

    private RxBus() {

    }

    public static RxBus getDefault() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Publishes an object to the specified subject for all subscribers of that subject.
     */
    public void publish(@NonNull String tag, @NonNull Object message) {
        getSubject(tag).onNext(new BusEvent(tag, message));
    }

    /**
     * Subscribes to the specified subject and listen for updates on that subject.
     * Pass in an object(often an Activity or Fragment) to associate
     * your registration with, so that you can dispose later.
     * <br/><br/>
     * <b>Note:</b> Make sure to call {@link RxBus#unregister(Object)} to avoid memory leaks.
     */
    public void subscribe(String tag, @NonNull Object lifecycle,
                          @NonNull Consumer<BusEvent> consumer,
                          Scheduler scheduler) {
        Disposable disposable = getSubject(tag)
                .observeOn(scheduler)
                .subscribe(consumer);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    /**
     * Unregisters this object from the bus, removing all subscriptions.
     * This should be called when the object is going to go out of memory.
     */
    public void unregister(@NonNull Object lifecycle) {
        CompositeDisposable compositeDisposable = mCompositeDisposableMap.remove(lifecycle);
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * Gets the CompositeDisposable or create it if not existed.
     *
     * @param lifecycle the key associated with the CompositeDisposable, often is an Activity or Fragment
     * @return the CompositeDisposable associated with the given key
     */
    private CompositeDisposable getCompositeDisposable(@NonNull Object lifecycle) {
        CompositeDisposable compositeDisposable = mCompositeDisposableMap.get(lifecycle);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            mCompositeDisposableMap.put(lifecycle, compositeDisposable);
        }
        return compositeDisposable;
    }

    /**
     * Gets the PublishSubject or create it if not existed.
     *
     * @param tag the key associated with the PublishSubject
     * @return the PublishSubject with the given tag
     */
    @NonNull
    private PublishSubject<BusEvent> getSubject(String tag) {
        PublishSubject<BusEvent> subject = mSubjectMap.get(tag);
        if (subject == null) {
            subject = PublishSubject.create();
            mSubjectMap.put(tag, subject);
        }
        return subject;
    }

    private static class SingletonHolder {
        static final RxBus INSTANCE = new RxBus();
    }

}
