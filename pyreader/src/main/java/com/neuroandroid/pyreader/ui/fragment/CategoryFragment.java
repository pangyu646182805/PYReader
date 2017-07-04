package com.neuroandroid.pyreader.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.CategoryList;
import com.neuroandroid.pyreader.mvp.contract.ICategoryContract;
import com.neuroandroid.pyreader.mvp.presenter.CategoryPresenter;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.FragmentUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class CategoryFragment extends BaseFragment<ICategoryContract.Presenter>
        implements ICategoryContract.View, MainActivity.MainActivityFragmentCallbacks {
    private static final int CATEGORY_SPAN_SIZE = 3;

    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.pb)
    ContentLoadingProgressBar mPb;

    private GridLayoutManager mGridLayoutManager;
    private List<CategoryList.MaleBean> mCategoryDataList = new ArrayList<>();
    private int size;

    private MainActivity.MainActivityFragmentCallbacks mCurrentFragment;

    @Override
    protected void initPresenter() {
        mPresenter = new CategoryPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(UIUtils.getString(R.string.category));
        mGridLayoutManager = new GridLayoutManager(mContext, CATEGORY_SPAN_SIZE);
        mRvCategory.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
    }

    @Override
    protected void initData() {
        mPresenter.getCategoryList();
    }

    @Override
    public void showCategoryList(CategoryList categoryList) {
        mPb.hide();
        size = categoryList.getMale().size() + 1;
        mCategoryDataList.clear();
        mCategoryDataList.add(null);
        mCategoryDataList.addAll(categoryList.getMale());
        mCategoryDataList.add(null);
        mCategoryDataList.addAll(categoryList.getFemale());
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return CategoryFragment.this.getSpanSize(position);
            }
        });
        mRvCategory.setLayoutManager(mGridLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(mContext, mCategoryDataList, null);
        mRvCategory.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener((holder, position, item) -> {
            if (item != null) {
                if (position < size) {
                    toCategoryListFragment(item.getName(), Constant.MALE);
                } else {
                    toCategoryListFragment(item.getName(), Constant.FEMALE);
                }
            }
        });
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
        mPb.hide();
    }

    private int getSpanSize(int position) {
        int spanSize;
        if (position == 0 || position == size) {
            spanSize = CATEGORY_SPAN_SIZE;
        } else {
            spanSize = 1;
        }
        return spanSize;
    }

    /**
     * 跳转到CategoryListFragment页面
     */
    private void toCategoryListFragment(String categoryName, String gender) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CATEGORY_NAME, categoryName);
        bundle.putString(Constant.GENDER, gender);
        categoryListFragment.setArguments(bundle);
        setCurrentFragment(categoryListFragment);
    }

    private void setCurrentFragment(Fragment fragment) {
        FragmentUtils.replaceFragment(getChildFragmentManager(), fragment, R.id.fl_container, false);
        mCurrentFragment = (MainActivity.MainActivityFragmentCallbacks) fragment;
    }

    /**
     * 返回true屏蔽一次返回事件
     */
    @Override
    public boolean handleBackPress() {
        if (mCurrentFragment != null && mCurrentFragment.handleBackPress()) {
            return true;
        } else {
            if (mCurrentFragment != null) {
                FragmentUtils.removeFragment((Fragment) mCurrentFragment);
                mCurrentFragment = null;
                return true;
            }
        }
        return false;
    }

    class CategoryAdapter extends BaseRvAdapter<CategoryList.MaleBean> {
        public CategoryAdapter(Context context, List<CategoryList.MaleBean> dataList, IMultiItemViewType<CategoryList.MaleBean> multiItemViewType) {
            super(context, dataList, multiItemViewType);
        }

        @Override
        public void convert(BaseViewHolder holder, CategoryList.MaleBean item, int position, int viewType) {
            switch (viewType) {
                case 0:
                    holder.setText(R.id.tv_category_title, item.getName())
                            .setText(R.id.tv_book_count, String.format(
                                    UIUtils.getString(R.string.category_book_count), item.getBookCount()));
                    break;
                case -1:
                    holder.setText(R.id.tv_text, position == 0 ? Constant.MALE_TEXT : Constant.FEMALE_TEXT);
                    break;
            }
        }

        @Override
        protected IMultiItemViewType<CategoryList.MaleBean> provideMultiItemViewType() {
            return new IMultiItemViewType<CategoryList.MaleBean>() {
                @Override
                public int getItemViewType(int position, CategoryList.MaleBean maleBean) {
                    return maleBean == null ? -1 : 0;
                }

                @Override
                public int getLayoutId(int viewType) {
                    if (viewType == -1) {
                        return R.layout.item_text;
                    }
                    return R.layout.item_category;
                }
            };
        }
    }
}
