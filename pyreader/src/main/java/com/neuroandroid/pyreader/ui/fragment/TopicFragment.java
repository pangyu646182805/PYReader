package com.neuroandroid.pyreader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.TopicBookListAdapter;
import com.neuroandroid.pyreader.base.BaseLazyFragment;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.mvp.contract.ITopicContract;
import com.neuroandroid.pyreader.mvp.presenter.TopicPresenter;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicFragment extends BaseLazyFragment<ITopicContract.Presenter> implements ITopicContract.View {
    private static final int PAGE_SIZE = 8;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView mRvTopicBookList;

    private int mCurrentTab;
    private String mDuration, mSort;
    private String mCurrentTag = "";
    private TopicBookListAdapter mTopicBookListAdapter;

    // 是否是下拉刷新
    private boolean isRefresh;
    private int mCurrentPage;

    public static TopicFragment newInstance(int tab) {
        TopicFragment topicFragment = new TopicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tab", tab);
        topicFragment.setArguments(bundle);
        return topicFragment;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TopicPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.layout_normal_recycler_view;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setHeaderView(new CustomRefreshHeader(mContext));
        mRefreshLayout.setBottomView(new BallPulseView(mContext));

        mRvTopicBookList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvTopicBookList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mTopicBookListAdapter = new TopicBookListAdapter(mContext, null, R.layout.item_books);
        mRvTopicBookList.setAdapter(mTopicBookListAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mTopicBookListAdapter != null && mTopicBookListAdapter.getDataList().isEmpty()) {
                mCurrentTab = getArguments().getInt("tab");
                switch (mCurrentTab) {
                    case 0:
                        mDuration = "last-seven-days";
                        mSort = "collectorCount";
                        break;
                    case 1:
                        mDuration = "all";
                        mSort = "created";
                        break;
                    case 2:
                    default:
                        mDuration = "all";
                        mSort = "collectorCount";
                        break;
                }
                loadData();
            }
        } else {
            hideLoading();
        }
    }

    private void loadData() {
        showLoading();
        mPresenter.getBookList(mDuration, mSort, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE), mCurrentTag);
    }

    /**
     * 根据tag重新筛选加载数据
     */
    public void reFilterLoadData(String tag) {
        mCurrentTag = tag;
        mCurrentPage = 0;
        mTopicBookListAdapter.clear();
        mRefreshLayout.startRefresh();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                mPresenter.getBookList(mDuration, mSort, getStringPage(0),
                        getStringPage(mTopicBookListAdapter.getItemCount() == 0 ? PAGE_SIZE : mTopicBookListAdapter.getItemCount()), mCurrentTag);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isRefresh = false;
                mPresenter.getBookList(mDuration, mSort, getStringPage(mCurrentPage), getStringPage(PAGE_SIZE), mCurrentTag);
            }
        });
        mTopicBookListAdapter.setOnItemClickListener((holder, position, item) ->
                ((TopicBookListFragment) getParentFragment()).openRecommendBookListFragment(item));
    }

    @Override
    public void showBookList(BookList bookList) {
        hideLoading();
        if (isRefresh) {
            mTopicBookListAdapter.replaceAll(bookList.getBookLists());
        } else {
            mTopicBookListAdapter.addAll(bookList.getBookLists());
        }
        mCurrentPage = mTopicBookListAdapter.getItemCount();
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
