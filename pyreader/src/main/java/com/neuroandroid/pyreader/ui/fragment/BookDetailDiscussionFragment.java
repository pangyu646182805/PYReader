package com.neuroandroid.pyreader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookDiscussionAdapter;
import com.neuroandroid.pyreader.base.BaseLazyFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.DiscussionList;
import com.neuroandroid.pyreader.mvp.contract.IBookDetailDiscussionContract;
import com.neuroandroid.pyreader.mvp.presenter.BookDetailDiscussionPresenter;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailDiscussionFragment extends BaseLazyFragment<IBookDetailDiscussionContract.Presenter>
        implements IBookDetailDiscussionContract.View {
    private static final int PAGE_SIZE = 8;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView mRvDiscussionList;

    private String mBookId;
    private int mCurrentPage;
    private String mSortBy = Constant.SortType.DEFAULT;
    // 是否是下拉刷新
    private boolean isRefresh;
    private BookDiscussionAdapter mBookDiscussionAdapter;

    public static BookDetailDiscussionFragment newInstance(String bookId) {
        BookDetailDiscussionFragment discussionFragment = new BookDetailDiscussionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BOOK_ID, bookId);
        discussionFragment.setArguments(bundle);
        return discussionFragment;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new BookDetailDiscussionPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.layout_normal_recycler_view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mBookDiscussionAdapter != null && mBookDiscussionAdapter.getDataList().isEmpty()) {
                mBookId = getArguments().getString(Constant.BOOK_ID, null);
                loadDiscussionList();
            }
        } else {
            hideLoading();
        }
    }

    @Override
    protected void initView() {
        mRefreshLayout.setHeaderView(new CustomRefreshHeader(mContext));
        mRefreshLayout.setBottomView(new BallPulseView(mContext));
        mRvDiscussionList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvDiscussionList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mBookDiscussionAdapter = new BookDiscussionAdapter(mContext, null, R.layout.item_book_detail_discussion);
        mRvDiscussionList.setAdapter(mBookDiscussionAdapter);
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                mPresenter.getBookDetailDiscussionList(mBookId, mSortBy, getStringPage(0), getStringPage(mBookDiscussionAdapter.getItemCount()));
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isRefresh = false;
                mPresenter.getBookDetailDiscussionList(mBookId, mSortBy, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE));
            }
        });
    }

    private void loadDiscussionList() {
        showLoading();
        mPresenter.getBookDetailDiscussionList(mBookId, mSortBy, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE));
    }

    @Override
    public void showDiscussionList(DiscussionList discussionList) {
        hideLoading();
        if (isRefresh) {
            mBookDiscussionAdapter.replaceAll(discussionList.getPosts());
        } else {
            mBookDiscussionAdapter.addAll(discussionList.getPosts());
        }
        mCurrentPage = mBookDiscussionAdapter.getItemCount();
    }

    @Override
    public void showTip(String tip) {
        hideLoading();
        showError(() -> loadDiscussionList());
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefreshing();
            mRefreshLayout.finishLoadmore();
        }
    }

    private String getStringPage(int page) {
        return String.valueOf(page);
    }
}
