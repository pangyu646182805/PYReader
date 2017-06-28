package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.CategoryList;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public interface ICategoryModel {
    Observable<CategoryList> getCategoryList();
}
