package com.neuroandroid.pyreader.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BooksByTagAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BooksByTag;
import com.neuroandroid.pyreader.mvp.contract.IBooksByTagContract;
import com.neuroandroid.pyreader.mvp.presenter.BooksByTagPresenter;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.NavigationUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BooksByTagFragment extends BaseFragment<IBooksByTagContract.Presenter> implements IBooksByTagContract.View {
    private static final int PAGE_SIZE = 8;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_book_list)
    RecyclerView mRvBookList;

    private String mBookTag;
    private int mCurrentPage;
    private BooksByTagAdapter mBooksByTagAdapter;
    // 是否是下拉刷新
    private boolean isRefresh;

    @Override
    protected void initPresenter() {
        mPresenter = new BooksByTagPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_books_by_tag;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        mRefreshLayout.setHeaderView(new CustomRefreshHeader(mContext));
        mRefreshLayout.setBottomView(new BallPulseView(mContext));
        mRvBookList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvBookList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mBooksByTagAdapter = new BooksByTagAdapter(mContext, null, R.layout.item_books_by_tag);
        mRvBookList.setAdapter(mBooksByTagAdapter);
    }

    @Override
    protected void initData() {
        mBookTag = getArguments().getString(Constant.BOOK_TAG, null);
        setToolbarTitle(mBookTag);
        showLoading();
        mPresenter.getBooksByTag(mBookTag, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE * 2));
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                mPresenter.getBooksByTag(mBookTag, getStringPage(0), getStringPage(mBooksByTagAdapter.getItemCount()));
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isRefresh = false;
                mPresenter.getBooksByTag(mBookTag, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE));
            }
        });
        mBooksByTagAdapter.setOnItemClickListener((holder, position, item) -> NavigationUtils.goToBookDetailPage(mActivity, item.getBookId()));
    }

    @Override
    public void showBookList(BooksByTag booksByTag) {
        hideLoading();
        if (isRefresh) {
            mBooksByTagAdapter.replaceAll(booksByTag.getBooks());
        } else {
            mBooksByTagAdapter.addAll(booksByTag.getBooks());
        }
        mCurrentPage = mBooksByTagAdapter.getItemCount();
    }

    @Override
    public void showTip(String tip) {
        hideLoading();
        showError(() -> {
            showLoading();
            mPresenter.getBooksByTag(mBookTag, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE));
        });
    }

    private String getStringPage(int page) {
        return String.valueOf(page);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefreshing();
            mRefreshLayout.finishLoadmore();
        }
    }
}
