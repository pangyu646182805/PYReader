package com.neuroandroid.pyreader.mvp.contract;

import com.neuroandroid.pyreader.base.IPresenter;
import com.neuroandroid.pyreader.base.IView;
import com.neuroandroid.pyreader.model.response.CategoryList;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ICategoryContract {
    interface Presenter extends IPresenter {
        /**
         * 获取分类
         */
        void getCategoryList();
    }

    interface View extends IView<Presenter> {
        /**
         * 展示书籍列表
         */
        void showCategoryList(CategoryList categoryList);
    }
}
