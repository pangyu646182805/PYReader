package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.mvp.model.IRecommendBookListDetailModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class RecommendBookListDetailModelImpl extends BaseModel implements IRecommendBookListDetailModel {
    public RecommendBookListDetailModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BookListDetail> getBookListDetail(String bookListId) {
        return mService.getBookListDetail(bookListId);
    }
}
