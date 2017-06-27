package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.mvp.contract.IBookDetailReviewContract;
import com.neuroandroid.pyreader.mvp.model.impl.BookDetailReviewModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailReviewPresenter extends BasePresenter<BookDetailReviewModelImpl, IBookDetailReviewContract.View>
        implements IBookDetailReviewContract.Presenter {
    public BookDetailReviewPresenter(IBookDetailReviewContract.View view) {
        super(view);
        mModel = new BookDetailReviewModelImpl(Constant.API_BASE_URL);
    }

    @Override
    public void getBookDetailReviewList(String book, String sort, String start, String limit) {
        getModelFilteredFactory(HotReview.class).compose(mModel.getBookDetailReviewList(book, sort, start, limit))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<HotReview>() {
                    @Override
                    protected void onHandleSuccess(HotReview hotReview) {
                        mView.showReviewList(hotReview);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
