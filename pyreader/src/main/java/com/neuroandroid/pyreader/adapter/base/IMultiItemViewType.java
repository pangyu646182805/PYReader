package com.neuroandroid.pyreader.adapter.base;

/**
 * Created by NeuroAndroid on 2017/6/15.
 */

public interface IMultiItemViewType<T> {
    /**
     * 返回不同position的viewType
     */
    int getItemViewType(int position, T t);

    /**
     * 返回不同viewType的布局id
     */
    int getLayoutId(int viewType);
}
