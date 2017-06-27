package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.HotReview;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBookDetailReviewContract {
    interface Presenter extends IPresenter {
        /**
         * 获取书籍详情书评列表
         * 分页获取
         */
        void getBookDetailReviewList(String book, String sort, String start, String limit);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示书籍列表
         */
        void showReviewList(HotReview hotReview);
    }
}
