package com.neuroandroid.pyreader.adapter.base;

/**
 * Created by NeuroAndroid on 2017/6/15.
 */

public interface ISelect {
    /**
     * 单选模式
     */
    int SINGLE_MODE = 1;
    /**
     * 多选模式
     */
    int MULTIPLE_MODE = 2;

    boolean isSelected();

    void setSelected(boolean selected);
}
