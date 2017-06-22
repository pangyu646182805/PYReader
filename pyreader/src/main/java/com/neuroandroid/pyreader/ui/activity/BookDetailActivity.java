package com.neuroandroid.pyreader.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

import com.google.gson.Gson;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookDetailAdapter;
import com.neuroandroid.pyreader.base.BaseActivity;
import com.neuroandroid.pyreader.base.BaseResponse;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;
import com.neuroandroid.pyreader.mvp.contract.IBookDetailContract;
import com.neuroandroid.pyreader.mvp.presenter.BookDetailPresenter;
import com.neuroandroid.pyreader.utils.ColorUtils;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class BookDetailActivity extends BaseActivity<IBookDetailContract.Presenter> implements IBookDetailContract.View {
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rv_book_detail)
    RecyclerView mRvBookDetail;
    private String mBookId;
    private List<BaseResponse> mBookDetailDataList = new ArrayList<>();
    private BookDetailAdapter mBookDetailAdapter;

    private int mScrollX;

    @Override
    protected void initPresenter() {
        mPresenter = new BookDetailPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.book_detail);
        setDisplayHomeAsUpEnabled();
        mRvBookDetail.setLayoutManager(new LinearLayoutManager(this));
        mRvBookDetail.addItemDecoration(DividerUtils.generateHorizontalDivider(this, R.dimen.y16, R.color.split));
        mBookDetailAdapter = new BookDetailAdapter(this, null, null);
        mBookDetailAdapter.clearRvAnim(mRvBookDetail);
        mRvBookDetail.setAdapter(mBookDetailAdapter);
    }

    @Override
    protected void initData() {
        mBookDetailDataList.add(null);
        mBookDetailDataList.add(null);
        mBookDetailDataList.add(null);
        mBookDetailDataList.add(null);
        mBookDetailAdapter.replaceAll(mBookDetailDataList);
        mBookId = getIntent().getStringExtra(Constant.INTENT_BOOK_ID);
        showLoading();
        mPresenter.getBookDetail(mBookId);
        mPresenter.getHotReview(mBookId);
        mPresenter.getRecommendBookList(mBookId, "3");
    }

    @Override
    protected void initListener() {
        mAppBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mBookDetailAdapter != null) mBookDetailAdapter.setAppBarLayoutHeight(mAppBarLayout.getHeight());
            }
        });
        mRvBookDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mBookDetailAdapter != null && mBookDetailAdapter.getBookDetailHeaderHeight() != -1) {
                    mScrollX = mScrollX + dy;  // 累加y值 解决滑动一半y值为0
                    if (mScrollX <= 0) {  // 设置标题的背景颜色
                        // getTitleBar().setBackgroundResource(R.drawable.bg_toolbar_shade);
                        mAppBarLayout.setBackgroundColor(Color.TRANSPARENT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setTranslationZ(0f);
                        }
                    } else if (mScrollX > 0 && mScrollX <= mBookDetailAdapter.getBookDetailHeaderHeight()) {
                        // 滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                        float scale = (float) mScrollX / mBookDetailAdapter.getBookDetailHeaderHeight();
                        mAppBarLayout.setBackgroundColor(ColorUtils.adjustAlpha(UIUtils.getColor(R.color.colorPrimary), scale));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setTranslationZ(scale * UIUtils.getDimen(R.dimen.y8));
                        }
                    } else {
                        mAppBarLayout.setBackgroundColor(UIUtils.getColor(R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBarLayout.setTranslationZ(UIUtils.getDimen(R.dimen.y8));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void showBookDetail(BookDetail bookDetail) {
        L.e("json : " + new Gson().toJson(bookDetail));
        mBookDetailDataList.set(0, bookDetail);
        if (mBookDetailAdapter != null)
            mBookDetailAdapter.set(BookDetailAdapter.VIEW_TYPE_BOOK_DETAIL_HEADER, bookDetail);
        hideLoadingAfterPresenter();
        mRvBookDetail.scrollToPosition(0);
    }

    @Override
    public void showHotReview(HotReview hotReview) {
        L.e("json : " + new Gson().toJson(hotReview));
        mBookDetailDataList.set(1, hotReview);
        if (mBookDetailAdapter != null)
            mBookDetailAdapter.set(BookDetailAdapter.VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW, hotReview);
        hideLoadingAfterPresenter();
        mRvBookDetail.scrollToPosition(0);
    }

    @Override
    public void showRecommendBookList(RecommendBookList recommendBookList) {
        L.e("json : " + new Gson().toJson(recommendBookList));
        mBookDetailDataList.set(3, recommendBookList);
        if (mBookDetailAdapter != null)
            mBookDetailAdapter.set(BookDetailAdapter.VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST, recommendBookList);
        hideLoadingAfterPresenter();
        mRvBookDetail.scrollToPosition(0);
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
        hideLoading();
    }

    /**
     * 设置app主色调Toolbar
     * 没有网络没有内容的情况下设置
     */
    private void setColorPrimaryToolbar() {
        mAppBarLayout.setBackgroundColor(UIUtils.getColor(R.color.colorPrimary));
    }

    private void hideLoadingAfterPresenter() {
        if (mBookDetailDataList.get(0) != null && mBookDetailDataList.get(1) != null
                && mBookDetailDataList.get(3) != null) {
            hideLoading();
        }
    }
}
