package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.CategoryListLv2;
import com.neuroandroid.pyreader.mvp.contract.ICategoryListContract;
import com.neuroandroid.pyreader.mvp.model.impl.CategoryListModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/7/4.
 */

public class CategoryListPresenter extends BasePresenter<CategoryListModelImpl, ICategoryListContract.View>
        implements ICategoryListContract.Presenter {
    public CategoryListPresenter(ICategoryListContract.View view) {
        super(view);
        mModel = new CategoryListModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getCategoryListLv2() {
        getModelFilteredFactory(CategoryListLv2.class).compose(mModel.getCategoryListLv2())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<CategoryListLv2>() {
                    @Override
                    protected void onHandleSuccess(CategoryListLv2 categoryListLv2) {
                        mView.showCategoryListLv2(categoryListLv2);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void getBooksByCategory(String gender, String type, String major, String minor, int start, int limit) {
        getModelFilteredFactory(BooksByCategory.class).compose(mModel.getBooksByCategory(gender, type, major,
                minor, start, limit)).compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BooksByCategory>() {
                    @Override
                    protected void onHandleSuccess(BooksByCategory booksByCategory) {
                        mView.showBooks(booksByCategory);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
