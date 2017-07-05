package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.mvp.model.ITopicModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicModelImpl extends BaseModel implements ITopicModel {
    public TopicModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<BookList> getBookList(String duration, String sort, String start,
                                            String limit, String tag, String gender) {
        return mService.getBookList(duration, sort, start, limit, tag, gender);
    }
}
