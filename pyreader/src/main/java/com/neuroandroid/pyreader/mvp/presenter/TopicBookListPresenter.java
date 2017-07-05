package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookListTags;
import com.neuroandroid.pyreader.mvp.contract.ITopicBookListContract;
import com.neuroandroid.pyreader.mvp.model.impl.TopicBookListModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicBookListPresenter extends BasePresenter<TopicBookListModelImpl, ITopicBookListContract.View>
        implements ITopicBookListContract.Presenter {
    public TopicBookListPresenter(ITopicBookListContract.View view) {
        super(view);
        mModel = new TopicBookListModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBookListTags() {
        getModelFilteredFactory(BookListTags.class).compose(mModel.getBookListTags())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BookListTags>() {
                    @Override
                    protected void onHandleSuccess(BookListTags bookListTags) {
                        mView.showBookListTags(bookListTags);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
