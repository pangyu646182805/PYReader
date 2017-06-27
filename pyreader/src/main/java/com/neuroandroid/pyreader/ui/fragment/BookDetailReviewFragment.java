package com.neuroandroid.pyreader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.HotReviewAdapter;
import com.neuroandroid.pyreader.base.BaseLazyFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.mvp.contract.IBookDetailReviewContract;
import com.neuroandroid.pyreader.mvp.presenter.BookDetailReviewPresenter;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailReviewFragment extends BaseLazyFragment<IBookDetailReviewContract.Presenter>
        implements IBookDetailReviewContract.View {
    private static final int PAGE_SIZE = 8;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_review_list)
    RecyclerView mRvReviewList;

    private String mBookId;
    private int mCurrentPage;
    private String mSortBy = Constant.SortType.DEFAULT;
    private HotReviewAdapter mBookDetailReviewAdapter;
    // 是否是下拉刷新
    private boolean isRefresh;

    public static BookDetailReviewFragment newInstance(String bookId) {
        BookDetailReviewFragment reviewFragment = new BookDetailReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BOOK_ID, bookId);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new BookDetailReviewPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_book_detail_review;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mBookDetailReviewAdapter != null && mBookDetailReviewAdapter.getDataList().isEmpty()) {
                mBookId = getArguments().getString(Constant.BOOK_ID, null);
                loadReviewList();
            }
        } else {
            hideLoading();
        }
    }

    @Override
    protected void initView() {
        mRefreshLayout.setHeaderView(new CustomRefreshHeader(mContext));
        mRefreshLayout.setBottomView(new BallPulseView(mContext));
        mRvReviewList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvReviewList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mBookDetailReviewAdapter = new HotReviewAdapter(mContext, null, R.layout.item_hot_review);
        mBookDetailReviewAdapter.setShowReviewTime(true);
        mRvReviewList.setAdapter(mBookDetailReviewAdapter);
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                mPresenter.getBookDetailReviewList(mBookId, mSortBy, getStringPage(0), getStringPage(mBookDetailReviewAdapter.getItemCount()));
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isRefresh = false;
                mPresenter.getBookDetailReviewList(mBookId, mSortBy, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE));
            }
        });
        mBookDetailReviewAdapter.setOnItemClickListener((holder, position, item) -> {

        });
    }

    /**
     * 加载书评列表
     */
    private void loadReviewList() {
        showLoading();
        mPresenter.getBookDetailReviewList(mBookId, mSortBy, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE));
    }

    @Override
    public void showReviewList(HotReview hotReview) {
        hideLoading();
        if (isRefresh) {
            mBookDetailReviewAdapter.replaceAll(hotReview.getReviews());
        } else {
            mBookDetailReviewAdapter.addAll(hotReview.getReviews());
        }
        mCurrentPage = mBookDetailReviewAdapter.getItemCount();
    }

    @Override
    public void showTip(String tip) {
        hideLoading();
        showError(() -> loadReviewList());
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
