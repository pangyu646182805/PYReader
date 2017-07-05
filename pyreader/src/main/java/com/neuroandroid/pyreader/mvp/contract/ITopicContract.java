package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BookList;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ITopicContract {
    interface Presenter extends IPresenter {
        /**
         * 获取主题书单列表
         */
        void getBookList(String duration, String sort,
                          String start, String limit,
                          String tag);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示主题书单列表
         */
        void showBookList(BookList bookList);
    }
}
