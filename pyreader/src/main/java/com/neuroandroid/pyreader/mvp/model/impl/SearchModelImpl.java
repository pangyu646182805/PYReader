package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.model.response.SearchBooks;
import com.neuroandroid.pyreader.mvp.model.ISearchModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public class SearchModelImpl extends BaseModel implements ISearchModel {
    public SearchModelImpl(String baseUrl, boolean needCache) {
        super(baseUrl, needCache);
    }

    @Override
    public Observable<HotWord> getHotWord() {
        return mService.getHotWord();
    }

    @Override
    public Observable<SearchBooks> searchBooks(String query) {
        return mService.searchBooks(query);
    }
}
