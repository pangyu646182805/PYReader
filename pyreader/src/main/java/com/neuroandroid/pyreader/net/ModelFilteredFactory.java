package com.neuroandroid.pyreader.net;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class ModelFilteredFactory<T> {
    private final ObservableTransformer TRANSFORMER = new SimpleTransformer();

    @SuppressWarnings("unchecked")
    public Observable<T> compose(Observable<T> observable) {
        return observable.compose(TRANSFORMER);
    }

    private class SimpleTransformer<T> implements ObservableTransformer<T, T> {
        @Override
        public ObservableSource apply(@NonNull Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .timeout(5, TimeUnit.SECONDS)  // 重连间隔时间
                    .retry(5);  // 重连次数
        }
    }
}
