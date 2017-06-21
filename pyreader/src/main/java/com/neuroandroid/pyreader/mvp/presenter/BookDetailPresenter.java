package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;
import com.neuroandroid.pyreader.mvp.contract.IBookDetailContract;
import com.neuroandroid.pyreader.mvp.model.impl.BookDetailModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class BookDetailPresenter extends BasePresenter<BookDetailModelImpl,
        IBookDetailContract.View> implements IBookDetailContract.Presenter {
    public BookDetailPresenter(IBookDetailContract.View view) {
        super(view);
        mModel = new BookDetailModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBookDetail(String bookId) {
        getModelFilteredFactory(BookDetail.class).compose(mModel.getBookDetail(bookId))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BookDetail>() {
                    @Override
                    protected void onHandleSuccess(BookDetail bookDetail) {
                        mView.showBookDetail(bookDetail);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void getHotReview(String book) {
        getModelFilteredFactory(HotReview.class).compose(mModel.getHotReview(book))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<HotReview>() {
                    @Override
                    protected void onHandleSuccess(HotReview hotReview) {
                        mView.showHotReview(hotReview);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void getRecommendBookList(String bookId, String limit) {
        getModelFilteredFactory(RecommendBookList.class).compose(mModel.getRecommendBookList(bookId, limit))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<RecommendBookList>() {
                    @Override
                    protected void onHandleSuccess(RecommendBookList recommendBookList) {
                        mView.showRecommendBookList(recommendBookList);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
