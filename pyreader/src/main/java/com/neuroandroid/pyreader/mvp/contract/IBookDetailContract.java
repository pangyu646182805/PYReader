package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IBookDetailContract {
    interface Presenter extends IPresenter {
        /**
         * 获取book详情
         */
        void getBookDetail(String bookId);

        /**
         * 获取热门评论
         */
        void getHotReview(String book);

        /**
         * 获取推荐书单
         */
        void getRecommendBookList(String bookId, String limit);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示book详情
         */
        void showBookDetail(BookDetail bookDetail);

        /**
         * 展示热门评论
         */
        void showHotReview(HotReview hotReview);

        /**
         * 展示推荐书单
         */
        void showRecommendBookList(RecommendBookList recommendBookList);
    }
}
