package com.neuroandroid.pyreader.base;


import android.support.annotation.NonNull;
import android.util.Log;

import com.neuroandroid.pyreader.exception.APIException;
import com.neuroandroid.pyreader.utils.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by NeuroAndroid on 2017/6/12.
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseResponse response) {
        if (response.isNoOk()) response.setOk(true);
        if (response.isOk()) {
            T data = (T) response;
            onHandleSuccess(data);
        } else {
            onHandleError("onNext()->error");
        }
    }

    @Override
    public void onError(Throwable e) {
        L.e("error:" + e.toString());
        if (e instanceof APIException) {
            APIException exception = (APIException) e;
            onHandleError(exception.getMessage());
        } else if (e instanceof UnknownHostException) {
            onHandleError("请打开网络");
        } else if (e instanceof SocketTimeoutException) {
            onHandleError("请求超时");
        } else if (e instanceof ConnectException) {
            onHandleError("连接失败");
        } else if (e instanceof HttpException) {
            onHandleError("请求超时");
        } else {
            onHandleError("请求失败");
        }
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        Log.e("main", "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    protected abstract void onHandleError(String tip);
}
