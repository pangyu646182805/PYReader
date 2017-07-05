package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.RankingList;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IRankingContract {
    interface Presenter extends IPresenter {
        /**
         * 获取所有排行榜
         */
        void getRanking();
    }

    interface View extends IView<Presenter> {
        /**
         * 展示所有排行榜
         */
        void showRanking(RankingList rankingList);
    }
}
