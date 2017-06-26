package com.neuroandroid.pyreader.mvp.presenter;

import com.neuroandroid.pyreader.base.BaseObserver;
import com.neuroandroid.pyreader.base.BasePresenter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.model.response.SearchBooks;
import com.neuroandroid.pyreader.mvp.contract.ISearchContract;
import com.neuroandroid.pyreader.mvp.model.impl.SearchModelImpl;
import com.neuroandroid.pyreader.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public class SearchPresenter extends BasePresenter<SearchModelImpl, ISearchContract.View> implements ISearchContract.Presenter {
    public SearchPresenter(ISearchContract.View view) {
        super(view);
        mModel = new SearchModelImpl(Constant.API_BASE_URL, false);
        mView.setPresenter(this);
    }

    @Override
    public void getHotWord() {
        getModelFilteredFactory(HotWord.class).compose(mModel.getHotWord())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<HotWord>() {
                    @Override
                    protected void onHandleSuccess(HotWord hotWord) {
                        mView.showHotWord(hotWord);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void searchBooks(String query) {
        getModelFilteredFactory(SearchBooks.class).compose(mModel.searchBooks(query))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<SearchBooks>() {
                    @Override
                    protected void onHandleSuccess(SearchBooks searchBooks) {
                        mView.showSearchResult(searchBooks);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
