package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.mvp.model.IBookReadModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/6.
 */

public class BookReadModelImpl extends BaseModel implements IBookReadModel {
    public BookReadModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BookMixAToc> getBookMixAToc(String bookId, String view) {
        return mService.getBookMixAToc(bookId, view);
    }

    @Override
    public Observable<ChapterRead> getChapterRead(String url) {
        return mService.getChapterRead(url);
    }
}
