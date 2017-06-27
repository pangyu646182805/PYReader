package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.BooksByTag;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBooksByTagModel {
    Observable<BooksByTag> getBooksByTag(String tags, String start, String limit);
}
