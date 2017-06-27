package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.mvp.contract.IRecommendBookListContract;
import com.neuroandroid.pyreader.mvp.model.impl.RecommendBookListModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class RecommendBookListPresenter extends BasePresenter<RecommendBookListModelImpl, IRecommendBookListContract.View>
        implements IRecommendBookListContract.Presenter {
    public RecommendBookListPresenter(IRecommendBookListContract.View view) {
        super(view);
        mModel = new RecommendBookListModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBookListDetail(String bookListId) {
        getModelFilteredFactory(BookListDetail.class).compose(mModel.getBookListDetail(bookListId))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BookListDetail>() {
                    @Override
                    protected void onHandleSuccess(BookListDetail bookListDetail) {
                        mView.showBookList(bookListDetail);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
