package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BooksByCategory;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IRankingBookListContract {
    interface Presenter extends IPresenter {
        /**
         * 获取单一排行榜
         */
        void getRanking(String rankingId);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示单一排行榜
         */
        void showRanking(BooksByCategory booksByCategory);
    }
}
