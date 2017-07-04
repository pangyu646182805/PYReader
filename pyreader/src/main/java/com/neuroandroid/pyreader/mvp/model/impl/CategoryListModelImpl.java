package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.CategoryListLv2;
import com.neuroandroid.pyreader.mvp.model.ICategoryListModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class CategoryListModelImpl extends BaseModel implements ICategoryListModel {
    public CategoryListModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<CategoryListLv2> getCategoryListLv2() {
        return mService.getCategoryListLv2();
    }

    @Override
    public Observable<BooksByCategory> getBooksByCategory(String gender, String type, String major, String minor, int start, int limit) {
        return mService.getBooksByCategory(gender, type, major, minor, start, limit);
    }
}
