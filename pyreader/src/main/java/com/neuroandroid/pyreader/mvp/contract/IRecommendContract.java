package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.Recommend;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IRecommendContract {
    interface Presenter extends IPresenter {
        /**
         * 获取首页推荐列表
         */
        void getRecommend(String gender);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示首页推荐列表
         */
        void showRecommendList(Recommend recommend);
    }
}
