package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.CategoryList;
import com.neuroandroid.pyreader.mvp.model.ICategoryModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class CategoryModelImpl extends BaseModel implements ICategoryModel {
    public CategoryModelImpl(String baseUrl, boolean needCache) {
        super(baseUrl, needCache);
    }

    @Override
    public Observable<CategoryList> getCategoryList() {
        return mService.getCategoryList();
    }
}
