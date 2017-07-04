package com.neuroandroid.pyreader.bean;

import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.config.Constant;

/**
 * Created by NeuroAndroid on 2017/7/4.
 */

public class MajorBean implements ISelect {
    private String categoryName;
    @Constant.MajorType
    private String categoryType;

    private boolean isSelected;

    public MajorBean(String categoryName, @Constant.MajorType String categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public MajorBean(String categoryName, String categoryType, boolean isSelected) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.isSelected = isSelected;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
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
