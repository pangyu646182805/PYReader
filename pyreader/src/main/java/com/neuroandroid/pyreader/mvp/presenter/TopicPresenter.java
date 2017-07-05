package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.manager.SettingManager;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.mvp.contract.ITopicContract;
import com.neuroandroid.pyreader.mvp.model.impl.TopicModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicPresenter extends BasePresenter<TopicModelImpl, ITopicContract.View>
        implements ITopicContract.Presenter {
    public TopicPresenter(ITopicContract.View view) {
        super(view);
        mModel = new TopicModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getBookList(String duration, String sort, String start, String limit, String tag) {
        getModelFilteredFactory(BookList.class).compose(mModel.getBookList(duration, sort, start, limit, tag,
                SettingManager.getChooseSex(UIUtils.getContext()))).compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<BookList>() {
                    @Override
                    protected void onHandleSuccess(BookList bookList) {
                        mView.showBookList(bookList);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
