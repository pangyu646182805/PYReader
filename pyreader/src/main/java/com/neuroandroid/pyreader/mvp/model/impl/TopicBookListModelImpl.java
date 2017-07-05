package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BookListTags;
import com.neuroandroid.pyreader.mvp.model.ITopicBookListModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicBookListModelImpl extends BaseModel implements ITopicBookListModel {
    public TopicBookListModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BookListTags> getBookListTags() {
        return mService.getBookListTags();
    }
}
