package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BooksByTag;
import com.neuroandroid.pyreader.mvp.contract.IBooksByTagContract;
import com.neuroandroid.pyreader.mvp.model.impl.BooksByTagModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BooksByTagPresenter extends BasePresenter<BooksByTagModelImpl, IBooksByTagContract.View>
        implements IBooksByTagContract.Presenter {
    public BooksByTagPresenter(IBooksByTagContract.View view) {
        super(view);
        mModel = new BooksByTagModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBooksByTag(String tags, String start, String limit) {
        getModelFilteredFactory(BooksByTag.class).compose(mModel.getBooksByTag(tags, start, limit))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BooksByTag>() {
                    @Override
                    protected void onHandleSuccess(BooksByTag booksByTag) {
                        mView.showBookList(booksByTag);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
