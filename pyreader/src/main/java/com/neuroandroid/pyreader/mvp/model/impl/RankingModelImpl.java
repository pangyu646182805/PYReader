package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.RankingList;
import com.neuroandroid.pyreader.mvp.model.IRankingModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class RankingModelImpl extends BaseModel implements IRankingModel {
    public RankingModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<RankingList> getRanking() {
        return mService.getRanking();
    }
}
