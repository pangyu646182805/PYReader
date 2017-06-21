package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBookDetailModel {
    Observable<BookDetail> getBookDetail(String bookId);

    Observable<HotReview> getHotReview(String book);

    Observable<RecommendBookList> getRecommendBookList(String bookId, String limit);
}
