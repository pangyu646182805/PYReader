package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BookListTags;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ITopicBookListContract {
    interface Presenter extends IPresenter {
        /**
         * 获取主题书单标签列表
         */
        void getBookListTags();
    }

    interface View extends IView<Presenter> {
        /**
         * 展示主题书单标签列表
         */
        void showBookListTags(BookListTags bookListTags);
    }
}
