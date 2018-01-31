package com.ittianyu.bcdnwatcher.common.utils;

import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yu on 2016/12/2.
 */
public class RxUtils {
    /**
     * subscribeOn io
     * observeOn mainThread
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> netScheduler() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * subscribeOn io
     * observeOn io
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> allIoScheduler() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
            }
        };
    }



    /**
     * If the publish is empty, it wll run onError and throw a NoSuchElementException
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> notEmptyOrError() {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.switchIfEmpty(new Observable<T>() {
                    @Override
                    protected void subscribeActual(Observer<? super T> observer) {
                        observer.onError(new NoSuchElementException());
                    }
                });
            }
        };
    }

    public static void dispose(Disposable disposable) {
        if (null != disposable && !disposable.isDisposed())
            disposable.dispose();
    }

    public static <T> Observable<T> fromCallable(final Callable<? extends T> supplier) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    T result = supplier.call();
                    e.onNext(result);
                    e.onComplete();
                } catch (Exception ex) {
                    if (!e.isDisposed())
                        e.onError(ex);
                }
            }
        });
    }
}
