package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.BookListDetail;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public interface IRecommendBookListDetailModel {
    Observable<BookListDetail> getBookListDetail(String bookListId);
}
