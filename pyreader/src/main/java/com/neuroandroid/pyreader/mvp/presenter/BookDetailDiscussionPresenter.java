package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.DiscussionList;
import com.neuroandroid.pyreader.mvp.contract.IBookDetailDiscussionContract;
import com.neuroandroid.pyreader.mvp.model.impl.BookDetailDiscussionModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailDiscussionPresenter extends BasePresenter<BookDetailDiscussionModelImpl, IBookDetailDiscussionContract.View>
        implements IBookDetailDiscussionContract.Presenter {
    public BookDetailDiscussionPresenter(IBookDetailDiscussionContract.View view) {
        super(view);
        mModel = new BookDetailDiscussionModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBookDetailDiscussionList(String book, String sort, String start, String limit) {
        getModelFilteredFactory(DiscussionList.class).compose(mModel.getBookDetailDiscussionList(book, sort, start, limit))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<DiscussionList>() {
                    @Override
                    protected void onHandleSuccess(DiscussionList discussionList) {
                        mView.showDiscussionList(discussionList);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
