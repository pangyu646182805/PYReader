package com.neuroandroid.pyreader.bean;

import com.neuroandroid.pyreader.adapter.base.ISelect;

/**
 * Created by NeuroAndroid on 2017/6/15.
 */

public class TextSelectBean implements ISelect {
    private boolean isSelected;
    private String text;

    public TextSelectBean() {
    }

    public TextSelectBean(String text, boolean isSelected) {
        this.text = text;
        this.isSelected = isSelected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
