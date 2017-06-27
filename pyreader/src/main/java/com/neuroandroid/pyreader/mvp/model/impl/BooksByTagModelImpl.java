package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BooksByTag;
import com.neuroandroid.pyreader.mvp.model.IBooksByTagModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BooksByTagModelImpl extends BaseModel implements IBooksByTagModel {
    public BooksByTagModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BooksByTag> getBooksByTag(String tags, String start, String limit) {
        return mService.getBooksByTag(tags, start, limit);
    }
}
