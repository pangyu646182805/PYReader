package com.neuroandroid.pyreader.base;

import com.neuroandroid.pyreader.net.ModelFilteredFactory;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by NeuroAndroid on 2017/6/13.
 */

public class BasePresenter<M extends IModel, V extends IView, T extends BaseResponse> implements IPresenter {
    protected CompositeDisposable mCompositeDisposable;
    protected final ModelFilteredFactory<T> mModelFilteredFactory;

    protected M mModel;
    protected V mView;

    public BasePresenter(V view) {
        this.mView = view;
        mModelFilteredFactory = new ModelFilteredFactory<>();
    }

    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        // 将所有disposable放入，集中处理
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        unDispose();
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }

    /**
     * 解除订阅
     */
    protected void unDispose() {
        if (mCompositeDisposable != null) {
            // 保证activity结束时取消所有正在执行的订阅
            mCompositeDisposable.clear();
        }
    }
}
