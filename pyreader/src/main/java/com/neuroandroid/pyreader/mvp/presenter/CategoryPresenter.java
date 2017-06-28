package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.CategoryList;
import com.neuroandroid.pyreader.mvp.contract.ICategoryContract;
import com.neuroandroid.pyreader.mvp.model.impl.CategoryModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class CategoryPresenter extends BasePresenter<CategoryModelImpl, ICategoryContract.View>
        implements ICategoryContract.Presenter {
    public CategoryPresenter(ICategoryContract.View view) {
        super(view);
        mModel = new CategoryModelImpl(Constant.API_BASE_URL, true);
        mView.setPresenter(this);
    }

    @Override
    public void getCategoryList() {
        getModelFilteredFactory(CategoryList.class).compose(mModel.getCategoryList())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<CategoryList>() {
                    @Override
                    protected void onHandleSuccess(CategoryList categoryList) {
                        mView.showCategoryList(categoryList);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
