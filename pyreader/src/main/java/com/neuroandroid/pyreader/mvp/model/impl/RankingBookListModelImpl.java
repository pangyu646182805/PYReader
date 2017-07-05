package com.neuroandroid.pyreader.mvp.model.impl;

import com.neuroandroid.pyreader.base.BaseModel;
import com.neuroandroid.pyreader.model.response.Rankings;
import com.neuroandroid.pyreader.mvp.model.IRankingBookListModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class RankingBookListModelImpl extends BaseModel implements IRankingBookListModel {
    public RankingBookListModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<Rankings> getRanking(String rankingId) {
        return mService.getRanking(rankingId);
    }
}
