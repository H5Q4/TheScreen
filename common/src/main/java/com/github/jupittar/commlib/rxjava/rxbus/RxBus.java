package com.github.jupittar.commlib.rxjava.rxbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * A simple event bus implemented by RxJava
 * @see <a href="https://android.jlelse.eu/how-to-make-an-event-bus-with-rxjava-and-rxandroid-b7e3c0391978">
 *     How to make an Event Bus with RxJava and RxAndroid</a>
 */
public class RxBus {

    private Map<String, Subject<BusEvent>> mSubjectMap = new ConcurrentHashMap<>();
    private Map<Object, CompositeDisposable> mCompositeDisposableMap = new ConcurrentHashMap<>();

    private RxBus() {

    }

    public static RxBus getDefault() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Publishes an object to the specified subject for all subscribers of that PublishSubject.
     */
    public void publish(@NonNull String tag, @NonNull Object message) {
        getSubject(tag, false).onNext(new BusEvent(tag, message));
    }

    /**
     * Publishes an object to the specified subject for all subscribers of that BehaviorSubject.
     */
    public void publishSticky(@NonNull String tag, @NonNull Object message) {
        getSubject(tag, true).onNext(new BusEvent(tag, message));
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
        Disposable disposable = getSubject(tag, false)
                .observeOn(scheduler)
                .subscribe(consumer);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    /**
     * Subscribes to the specified subject and listen for updates on that subject.
     * Pass in an object(often an Activity or Fragment) to associate
     * your registration with, so that you can dispose later.
     * <br/><br/>
     * <b>Note:</b> Make sure to call {@link RxBus#unregister(Object)} to avoid memory leaks.
     */
    public void subscribe(String tag, @NonNull Object lifecycle,
                          @NonNull Consumer<BusEvent> consumer) {
        Disposable disposable = getSubject(tag, false)
                .subscribe(consumer);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    /**
     * Sticky version of {@link RxBus#subscribe(String, Object, Consumer, Scheduler)}.
     */
    public void subscribeSticky(String tag, @NonNull Object lifecycle,
                                @NonNull Consumer<BusEvent> consumer,
                                Scheduler scheduler) {
        Disposable disposable = getSubject(tag, true)
                .observeOn(scheduler)
                .subscribe(consumer);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    /**
     * Sticky version of {@link RxBus#subscribe(String, Object, Consumer)}.
     */
    public void subscribeSticky(String tag, @NonNull Object lifecycle,
                                @NonNull Consumer<BusEvent> consumer) {
        Disposable disposable = getSubject(tag, true)
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
    private Subject<BusEvent> getSubject(String tag, boolean isSticky) {
        Subject<BusEvent> subject = mSubjectMap.get(tag);
        if (subject == null) {
            if (isSticky) {
                subject = BehaviorSubject.create();
            } else {
                subject = PublishSubject.create();
            }
            mSubjectMap.put(tag, subject);
        }
        return subject;
    }

    /**
     * Empties this bus.
     */
    public void reset() {
        mSubjectMap.clear();
        mCompositeDisposableMap.clear();
    }


    private static class SingletonHolder {
        static final RxBus INSTANCE = new RxBus();
    }

}
