package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.mvp.contract.IRecommendBookListDetailContract;
import com.neuroandroid.pyreader.mvp.model.impl.RecommendBookListDetailModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class RecommendBookListDetailPresenter extends BasePresenter<RecommendBookListDetailModelImpl, IRecommendBookListDetailContract.View>
        implements IRecommendBookListDetailContract.Presenter {
    public RecommendBookListDetailPresenter(IRecommendBookListDetailContract.View view) {
        super(view);
        mModel = new RecommendBookListDetailModelImpl(Constant.API_BASE_URL);
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
