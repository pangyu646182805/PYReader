package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.model.IRecommendModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class RecommendModelImpl extends BaseModel implements IRecommendModel {
    public RecommendModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<Recommend> getRecommend(String gender) {
        return mService.getRecommend(gender);
    }
}
