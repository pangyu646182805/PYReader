package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.contract.IRecommendContract;
import com.neuroandroid.pyreader.mvp.model.impl.RecommendModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class RecommendPresenter extends BasePresenter<RecommendModelImpl, IRecommendContract.View, Recommend> implements IRecommendContract.Presenter {
    public RecommendPresenter(IRecommendContract.View view) {
        super(view);
        mModel = new RecommendModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getRecommend(String gender) {
        mModelFilteredFactory.compose(mModel.getRecommend(gender))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<Recommend>() {
                    @Override
                    protected void onHandleSuccess(Recommend recommend) {
                        mView.showRecommendList(recommend);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
