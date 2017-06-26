package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.model.response.SearchBooks;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ISearchContract {
    interface Presenter extends IPresenter {
        /**
         * 获取热搜书籍
         */
        void getHotWord();

        /**
         * 根据关键字查询书籍
         */
        void searchBooks(String query);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示热搜书籍
         */
        void showHotWord(HotWord hotWord);

        /**
         * 展示搜索结果
         */
        void showSearchResult(SearchBooks searchBooks);
    }
}
