package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.RankingList;
import com.neuroandroid.pyreader.mvp.contract.IRankingContract;
import com.neuroandroid.pyreader.mvp.model.impl.RankingModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class RankingPresenter extends BasePresenter<RankingModelImpl, IRankingContract.View>
        implements IRankingContract.Presenter {
    public RankingPresenter(IRankingContract.View view) {
        super(view);
        mModel = new RankingModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getRanking() {
        getModelFilteredFactory(RankingList.class).compose(mModel.getRanking())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<RankingList>() {
                    @Override
                    protected void onHandleSuccess(RankingList rankingList) {
                        mView.showRanking(rankingList);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
