package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.CategoryListLv2;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public interface ICategoryListModel {
    Observable<CategoryListLv2> getCategoryListLv2();

    Observable<BooksByCategory> getBooksByCategory(String gender, String type,
                                                   String major, String minor,
                                                   int start, int limit);
}
