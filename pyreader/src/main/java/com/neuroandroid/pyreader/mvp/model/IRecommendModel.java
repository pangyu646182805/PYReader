package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.Recommend;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IRecommendModel {
    Observable<Recommend> getRecommend(String gender);
}
