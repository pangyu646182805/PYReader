package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BookListDetail;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IRecommendBookListContract {
    interface Presenter extends IPresenter {
        /**
         * 获取书单详情
         */
        void getBookListDetail(String bookListId);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示书单列表
         */
        void showBookList(BookListDetail bookListDetail);
    }
}
