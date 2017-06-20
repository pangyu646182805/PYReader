package com.neuroandroid.pyreader.base;

import com.neuroandroid.pyreader.widget.StateLayout;

/**
 * Created by NeuroAndroid on 2017/6/13.
 */

public interface IView<T extends IPresenter> {
    /**
     * 设置presenter
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示错误状态
     */
    void showError(StateLayout.OnRetryListener onRetryListener);

    /**
     * 显示提示
     */
    void showTip(String tip);
}
