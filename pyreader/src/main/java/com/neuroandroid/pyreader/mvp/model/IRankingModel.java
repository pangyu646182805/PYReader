package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.RankingList;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public interface IRankingModel {
    Observable<RankingList> getRanking();
}
