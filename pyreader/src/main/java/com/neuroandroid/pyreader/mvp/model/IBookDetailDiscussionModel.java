package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.DiscussionList;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public interface IBookDetailDiscussionModel {
    Observable<DiscussionList> getBookDetailDiscussionList(String book, String sort, String start, String limit);
}
