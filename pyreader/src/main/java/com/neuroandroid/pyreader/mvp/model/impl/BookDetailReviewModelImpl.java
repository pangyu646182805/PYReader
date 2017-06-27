package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.mvp.model.IBookDetailReviewModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailReviewModelImpl extends BaseModel implements IBookDetailReviewModel {
    public BookDetailReviewModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<HotReview> getBookDetailReviewList(String book, String sort, String start, String limit) {
        return mService.getBookDetailReviewList(book, sort, start, limit);
    }
}
