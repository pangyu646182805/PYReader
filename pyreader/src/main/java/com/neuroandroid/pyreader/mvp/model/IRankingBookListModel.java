package com.neuroandroid.pyreader.mvp.model;

import com.neuroandroid.pyreader.model.response.Rankings;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public interface IRankingBookListModel {
    Observable<Rankings> getRanking(String rankingId);
}
