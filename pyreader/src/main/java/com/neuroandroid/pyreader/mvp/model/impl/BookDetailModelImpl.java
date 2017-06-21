package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;
import com.neuroandroid.pyreader.mvp.model.IBookDetailModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class BookDetailModelImpl extends BaseModel implements IBookDetailModel {
    public BookDetailModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BookDetail> getBookDetail(String bookId) {
        return mService.getBookDetail(bookId);
    }

    @Override
    public Observable<HotReview> getHotReview(String book) {
        return mService.getHotReview(book);
    }

    @Override
    public Observable<RecommendBookList> getRecommendBookList(String bookId, String limit) {
        return mService.getRecommendBookList(bookId, limit);
    }
}
