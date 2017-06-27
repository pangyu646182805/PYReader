package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.HotReview;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public interface IBookDetailReviewModel {
    Observable<HotReview> getBookDetailReviewList(String book, String sort, String start, String limit);
}
