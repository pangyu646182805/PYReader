package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.DiscussionList;
import com.neuroandroid.pyreader.mvp.model.IBookDetailDiscussionModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailDiscussionModelImpl extends BaseModel implements IBookDetailDiscussionModel {
    public BookDetailDiscussionModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<DiscussionList> getBookDetailDiscussionList(String book, String sort, String start, String limit) {
        return mService.getBookDetailDiscussionList(book, sort, "normal,vote", start, limit);
    }
}
