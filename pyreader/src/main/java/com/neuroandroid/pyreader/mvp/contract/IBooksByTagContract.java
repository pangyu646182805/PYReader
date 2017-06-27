package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BooksByTag;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBooksByTagContract {
    interface Presenter extends IPresenter {
        /**
         * 根据tag获取书籍列表
         * 分页获取
         */
        void getBooksByTag(String tags, String start, String limit);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示书籍列表
         */
        void showBookList(BooksByTag booksByTag);
    }
}
