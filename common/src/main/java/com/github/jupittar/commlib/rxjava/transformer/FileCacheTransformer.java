package com.github.jupittar.commlib.rxjava.transformer;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;


public class FileCacheTransformer<R> implements ObservableTransformer<R, R> {

    private final String mFilename;
    private final Context mContext;

    public FileCacheTransformer(String filename, Context context) {
        mFilename = filename;
        mContext = context;
    }

    public static <R> FileCacheTransformer<R> cacheToLocalFile(String filename, Context context) {
        return new FileCacheTransformer<R>(filename, context);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return readFromFile()
                .onExceptionResumeNext(
                        upstream
                                .take(1)
                                .doOnNext(this::saveToFile)
                );
    }

    private void saveToFile(R r) throws Exception {
        ObjectOutputStream objectOutputStream = null;
        try {
            final FileOutputStream fileOutputStream = new
                    FileOutputStream(getFilename());
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(r);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Observable<R> readFromFile() {
        return Observable.create(emitter -> {
            ObjectInputStream objectInputStream = null;
            try {
                final FileInputStream fileInputStream = new FileInputStream(getFilename());
                objectInputStream = new ObjectInputStream(fileInputStream);
                R object = (R) objectInputStream.readObject();
                emitter.onNext(object);
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                emitter.onComplete();
            }
        });
    }

    private String getFilename() {
        return mContext.getFilesDir().getAbsolutePath() + File.separator +
                mFilename;
    }

}
