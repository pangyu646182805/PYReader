package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.model.response.ChapterRead;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/6.
 */

public interface IBookReadModel {
    Observable<BookMixAToc> getBookMixAToc(String bookId, String view);

    Observable<ChapterRead> getChapterRead(String url);
}
