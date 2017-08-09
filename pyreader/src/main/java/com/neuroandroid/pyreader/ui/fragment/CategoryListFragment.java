package com.neuroandroid.pyreader.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BooksByCategoryAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.bean.MajorBean;
import com.neuroandroid.pyreader.bean.TextSelectBean;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BooksByCategory;
import com.neuroandroid.pyreader.model.response.CategoryListLv2;
import com.neuroandroid.pyreader.mvp.contract.ICategoryListContract;
import com.neuroandroid.pyreader.mvp.presenter.CategoryListPresenter;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.NavigationUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/4.
 */

public class CategoryListFragment extends BaseFragment<ICategoryListContract.Presenter>
        implements ICategoryListContract.View, MainActivity.MainActivityFragmentCallbacks {
    private static final int PAGE_SIZE = 16;

    private List<MajorBean> mMajorDataList;
    private List<TextSelectBean> mCategoryDataList = new ArrayList<>();

    @BindView(R.id.btn_filter)
    FloatingActionButton mBtnFilter;
    @BindView(R.id.rv_major)
    RecyclerView mRvMajor;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.blur_view)
    RealtimeBlurView mBlurView;
    @BindView(R.id.rl_filter)
    RelativeLayout mRlFilter;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_book_list)
    RecyclerView mRvBookList;

    private String mCategoryGender;
    private String mCategoryName;
    private int mLlContainerHeight;
    private boolean mFabOpen;
    private MajorAdapter mMajorAdapter;
    private String mType = Constant.MajorType.HOT;
    private String mMinor = "";
    // 是否是下拉刷新
    private boolean isRefresh;
    private int mCurrentPage;
    private BooksByCategoryAdapter mBooksByCategoryAdapter;

    @Override
    protected void initPresenter() {
        mPresenter = new CategoryListPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_category_list;
    }

    @Override
    protected void initView() {
        mRootView.setBackgroundColor(ThemeUtils.getBackgroundColor());

        if (ThemeUtils.isDarkMode())
            mBtnFilter.setBackgroundTintList(ColorStateList.valueOf(UIUtils.getColor(R.color.black)));

        setDisplayHomeAsUpEnabled();
        mRefreshLayout.setHeaderView(new CustomRefreshHeader(mContext));
        mRefreshLayout.setBottomView(new BallPulseView(mContext));
        mRvMajor.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCategory.setLayoutManager(new LinearLayoutManager(mContext));
        mRvBookList.setLayoutManager(new LinearLayoutManager(mContext));

        mMajorDataList = Arrays.asList(
                new MajorBean(Constant.HOT, Constant.MajorType.HOT, true),
                new MajorBean(Constant.NEW, Constant.MajorType.NEW, false),
                new MajorBean(Constant.REPUTATION, Constant.MajorType.REPUTATION, false),
                new MajorBean(Constant.OVER, Constant.MajorType.OVER, false));

        mMajorAdapter = new MajorAdapter(mContext, mMajorDataList, R.layout.item_fab);
        mMajorAdapter.clearRvAnim(mRvMajor);
        mRvMajor.setAdapter(mMajorAdapter);

        mBooksByCategoryAdapter = new BooksByCategoryAdapter(mContext, null, R.layout.item_books);
        mRvBookList.setAdapter(mBooksByCategoryAdapter);

        mBtnFilter.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnFilter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mBtnFilter.setTranslationY(mBtnFilter.getHeight() + UIUtils.getDimen(R.dimen.y32));
            }
        });
    }

    @Override
    protected void initData() {
        mCategoryName = getArguments().getString(Constant.CATEGORY_NAME);
        mCategoryGender = getArguments().getString(Constant.GENDER, Constant.MALE);
        setToolbarTitle(mCategoryName);
        mPresenter.getCategoryListLv2();
        loadBooksData();
    }

    /**
     * 加载图书列表
     */
    private void loadBooksData() {
        mRefreshLayout.startRefresh();
    }

    @Override
    protected void initListener() {
        mBtnFilter.setOnClickListener(view -> openOrCloseFab());
        mMajorAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<MajorBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, MajorBean majorBean) {
                if (isSelected) {
                    openOrCloseFab();
                    mType = majorBean.getCategoryType();
                    mCurrentPage = 0;
                    loadBooksData();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                mPresenter.getBooksByCategory(mCategoryGender, mType, mCategoryName, mMinor, 0, PAGE_SIZE);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isRefresh = false;
                mPresenter.getBooksByCategory(mCategoryGender, mType, mCategoryName, mMinor, mCurrentPage, PAGE_SIZE);
            }
        });
        mBooksByCategoryAdapter.setOnItemClickListener((holder, position, item) -> NavigationUtils.goToBookDetailPage(mActivity, item.getBookId(), false));
        mRlFilter.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (mFabOpen) openOrCloseFab();
                    break;
            }
            return false;
        });
    }

    private void openOrCloseFab() {
        if (!mFabOpen) {  // 展开fab
            mFabOpen = true;
            mRlFilter.setClickable(true);
            mBlurView.setBlurRadius(80);
            mBlurView.setOverlayColor(UIUtils.getColor(R.color.white_3));
            ViewCompat.animate(mBtnFilter).rotation(45).setDuration(120)
                    .setInterpolator(new BounceInterpolator()).start();

            int rightMargin = (int) ((mRootView.getWidth() - mBtnFilter.getWidth()) * 0.5f);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBtnFilter.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofInt(params.rightMargin, rightMargin);
            animator.addUpdateListener(valueAnimator -> {
                params.rightMargin = (int) valueAnimator.getAnimatedValue();
                mBtnFilter.setLayoutParams(params);
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mLlContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ValueAnimator animator = ValueAnimator.ofInt(0, mLlContainerHeight);
                    animator.addUpdateListener(valueAnimator -> {
                        mLlContainer.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                        mLlContainer.requestLayout();
                    });
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.setDuration(250);
                    animator.start();
                }
            });
            animator.setDuration(120);
            animator.start();
        } else {
            mFabOpen = false;
            mRlFilter.setClickable(false);
            ViewCompat.animate(mBtnFilter).rotation(0).setDuration(120)
                    .setInterpolator(new BounceInterpolator()).start();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBtnFilter.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofInt(params.rightMargin, (int) UIUtils.getDimen(R.dimen.x32));
            animator.addUpdateListener(valueAnimator -> {
                params.rightMargin = (int) valueAnimator.getAnimatedValue();
                mBtnFilter.setLayoutParams(params);
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mBlurView.setBlurRadius(0);
                    mBlurView.setOverlayColor(UIUtils.getColor(R.color.transparent));
                    ValueAnimator animator = ValueAnimator.ofInt(mLlContainerHeight, 0);
                    animator.addUpdateListener(valueAnimator -> {
                        if (mLlContainer != null) {
                            mLlContainer.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                            mLlContainer.requestLayout();
                        }
                    });
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.setDuration(250);
                    animator.start();
                }
            });
            animator.setDuration(120);
            animator.start();
        }
    }

    @Override
    public boolean handleBackPress() {
        if (mFabOpen) {
            openOrCloseFab();
            return true;
        }
        return false;
    }

    @Override
    public void showCategoryListLv2(CategoryListLv2 categoryList) {
        mCategoryDataList.clear();
        mCategoryDataList.add(new TextSelectBean("全部", true));
        if (Constant.MALE.equals(mCategoryGender)) {
            for (CategoryListLv2.MaleBean maleBean : categoryList.getMale()) {
                if (mCategoryName.equals(maleBean.getMajor())) {
                    for (String category : maleBean.getMins()) {
                        mCategoryDataList.add(new TextSelectBean(category, false));
                    }
                    break;
                }
            }
        } else {
            for (CategoryListLv2.MaleBean maleBean : categoryList.getFemale()) {
                if (mCategoryName.equals(maleBean.getMajor())) {
                    for (String category : maleBean.getMins()) {
                        mCategoryDataList.add(new TextSelectBean(category, false));
                    }
                    break;
                }
            }
        }
        if (mCategoryDataList.size() == 1) {
            mCategoryDataList.clear();
        }
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(mContext, mCategoryDataList, R.layout.item_fab);
        mRvCategory.setAdapter(categoryListAdapter);
        categoryListAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<TextSelectBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, TextSelectBean textSelectBean) {
                if (isSelected) {
                    openOrCloseFab();
                    mMinor = textSelectBean.getText();
                    mCurrentPage = 0;
                    loadBooksData();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mLlContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLlContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mLlContainerHeight = mLlContainer.getHeight();
                mLlContainer.getLayoutParams().height = 0;
                mLlContainer.requestLayout();
            }
        });
    }

    @Override
    public void showBooks(BooksByCategory booksByCategory) {
        L.e("json : " + new Gson().toJson(booksByCategory));
        ViewCompat.animate(mBtnFilter).translationY(0).setDuration(200).start();
        hideLoading();
        if (isRefresh) {
            mBooksByCategoryAdapter.replaceAll(booksByCategory.getBooks());
        } else {
            mBooksByCategoryAdapter.addAll(booksByCategory.getBooks());
        }
        mCurrentPage = mBooksByCategoryAdapter.getItemCount();
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
        hideLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefreshing();
            mRefreshLayout.finishLoadmore();
        }
    }

    private class MajorAdapter extends SelectAdapter<MajorBean> {
        private final TypedValue mTypedValue;

        public MajorAdapter(Context context, List<MajorBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
            mTypedValue = new TypedValue();
            mContext.getTheme().resolveAttribute(R.attr.colorPrimary, mTypedValue, true);
        }

        @Override
        public void convert(BaseViewHolder holder, MajorBean item, int position, int viewType) {
            holder.setTextColor(R.id.tv_fab, item.isSelected() ? mTypedValue.data :
                    UIUtils.getColor(R.color.colorGray333))
                    .setText(R.id.tv_fab, item.getCategoryName());
        }
    }

    private class CategoryListAdapter extends SelectAdapter<TextSelectBean> {
        private final TypedValue mTypedValue;

        public CategoryListAdapter(Context context, List<TextSelectBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
            mTypedValue = new TypedValue();
            mContext.getTheme().resolveAttribute(R.attr.colorPrimary, mTypedValue, true);
        }

        @Override
        public void convert(BaseViewHolder holder, TextSelectBean item, int position, int viewType) {
            holder.setTextColor(R.id.tv_fab, item.isSelected() ? mTypedValue.data :
                    UIUtils.getColor(R.color.colorGray333))
                    .setText(R.id.tv_fab, item.getText());
        }
    }
}
