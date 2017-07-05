package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.Rankings;
import com.neuroandroid.pyreader.mvp.contract.IRankingBookListContract;
import com.neuroandroid.pyreader.mvp.model.impl.RankingBookListModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class RankingBookListPresenter extends BasePresenter<RankingBookListModelImpl, IRankingBookListContract.View>
        implements IRankingBookListContract.Presenter {
    public RankingBookListPresenter(IRankingBookListContract.View view) {
        super(view);
        mModel = new RankingBookListModelImpl(Constant.API_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getRanking(String rankingId) {
        getModelFilteredFactory(Rankings.class).compose(mModel.getRanking(rankingId))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<Rankings>() {
                    @Override
                    protected void onHandleSuccess(Rankings rankings) {
                        List<Rankings.RankingBean.BooksBean> books = rankings.getRanking().getBooks();

                        BooksByCategory booksByCategory = new BooksByCategory();
                        List<BooksByCategory.BooksBean> dataList = new ArrayList<>();
                        for (Rankings.RankingBean.BooksBean bean : books) {
                            dataList.add(new BooksByCategory.BooksBean(bean.getBookId(), bean.getCover(), bean.getTitle(),
                                    bean.getAuthor(), bean.getCat(), bean.getShortIntro(),
                                    bean.getLatelyFollower(), bean.getRetentionRatio()));
                        }
                        booksByCategory.setBooks(dataList);
                        mView.showRanking(booksByCategory);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
