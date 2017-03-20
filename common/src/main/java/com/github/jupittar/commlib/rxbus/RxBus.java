package com.github.jupittar.commlib.rxbus;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * A simple event bus implemented by RxJava
 * <p>
 * Created by Jupittar on 2016/4/29.
 */
public class RxBus {

  private final Subject<Object> mBus = PublishSubject.create().toSerialized();

  private RxBus() {

  }

  public static RxBus getDefault() {
    return SingletonHolder.INSTANCE;
  }

  public void post(String tag, Object o) {
    if (mBus.hasObservers()) {
      mBus.onNext(new BusEvent(tag, o));
    }
  }

  @SuppressWarnings("unchecked")
  public <T> Observable<T> toObservable(final String tag, final Class<T> tClass) {
    return mBus.filter(new Predicate<Object>() {
      @Override
      public boolean test(@NonNull Object o) throws Exception {
        if (!(o instanceof BusEvent)) {
          return false;
        }
        BusEvent busEvent = (BusEvent) o;
        return tClass.isInstance(busEvent.getObject())
            && tag != null && tag.equals(busEvent.getTag());
      }
    }).map(new Function<Object, T>() {
      @Override
      public T apply(@NonNull Object o) throws Exception {
        BusEvent busEvent = (BusEvent) o;
        return (T) busEvent.getObject();
      }
    });
  }

  private static class SingletonHolder {
    static final RxBus INSTANCE = new RxBus();
  }

}
