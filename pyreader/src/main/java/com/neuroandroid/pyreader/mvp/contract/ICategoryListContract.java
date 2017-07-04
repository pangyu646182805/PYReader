package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.CategoryListLv2;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ICategoryListContract {
    interface Presenter extends IPresenter {
        /**
         * 获取分类
         */
        void getCategoryListLv2();

        /**
         * 根据category获取书集
         */
        void getBooksByCategory(String gender, String type,
                                String major, String minor,
                                int start, int limit);
    }

    interface View extends IView<Presenter> {
        /**
         * 展示书籍分类
         */
        void showCategoryListLv2(CategoryListLv2 categoryList);

        /**
         * 展示书籍
         */
        void showBooks(BooksByCategory booksByCategory);
    }
}
