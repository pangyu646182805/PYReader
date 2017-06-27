package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.DiscussionList;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBookDetailDiscussionContract {
    interface Presenter extends IPresenter {
        /**
         * 获取书籍详情讨论列表
         * 分页获取
         */
        void getBookDetailDiscussionList(String book, String sort, String start, String limit);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示评论列表
         */
        void showDiscussionList(DiscussionList discussionList);
    }
}
