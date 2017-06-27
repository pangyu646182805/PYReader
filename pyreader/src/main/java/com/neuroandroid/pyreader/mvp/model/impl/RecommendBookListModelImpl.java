package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.mvp.model.IRecommendBookListModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class RecommendBookListModelImpl extends BaseModel implements IRecommendBookListModel {
    public RecommendBookListModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BookListDetail> getBookListDetail(String bookListId) {
        return mService.getBookListDetail(bookListId);
    }
}
