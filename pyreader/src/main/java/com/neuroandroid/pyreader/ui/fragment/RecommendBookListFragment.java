package com.neuroandroid.pyreader.ui.fragment;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.mvp.contract.IRecommendBookListContract;
import com.neuroandroid.pyreader.mvp.presenter.RecommendBookListPresenter;

/**
 * Created by NeuroAndroid on 2017/6/27.
 * 推荐书单页面
 */
public class RecommendBookListFragment extends BaseFragment<IRecommendBookListContract.Presenter>
        implements IRecommendBookListContract.View {
    @Override
    protected void initPresenter() {
        mPresenter = new RecommendBookListPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_recommend_book_list;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showBookList(BookListDetail bookListDetail) {

    }
}
