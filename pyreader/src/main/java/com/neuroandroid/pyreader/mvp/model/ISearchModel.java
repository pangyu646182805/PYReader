package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.model.response.SearchBooks;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public interface ISearchModel {
    Observable<HotWord> getHotWord();

    Observable<SearchBooks> searchBooks(String query);
}
