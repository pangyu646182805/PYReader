package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.BookList;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public interface ITopicModel {
    Observable<BookList> getBookList(String duration, String sort,
                                     String start, String limit,
                                     String tag, String gender);
}
