package com.neuroandroid.pyreader.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDetailCommunityFragment extends BaseFragment {
    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    private String mBookId;
    private String mBookTitle;
    private int mIndex;
    private FragmentPagerAdapter mPagerAdapter;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private List<String> mPageTitles = Arrays.asList(UIUtils.getString(R.string.discussion),
            UIUtils.getString(R.string.review));

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_book_detail_community;
    }

    @Override
    protected void initView() {
        mRootView.setBackgroundColor(ThemeUtils.getBackgroundColor());
        setDisplayHomeAsUpEnabled();
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mBookId = bundle.getString(Constant.BOOK_ID, null);
        mBookTitle = bundle.getString(Constant.BOOK_TITLE, null);
        mIndex = bundle.getInt(Constant.BOOK_DETAIL_COMMUNITY_INDEX);
        setToolbarTitle(mBookTitle);
        initViewPager();
    }

    private void initViewPager() {
        mFragments.add(BookDetailDiscussionFragment.newInstance(mBookId));
        mFragments.add(BookDetailReviewFragment.newInstance(mBookId));
        mPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mPageTitles.get(position);
            }
        };
        mVpContent.setAdapter(mPagerAdapter);
        mVpContent.setOffscreenPageLimit(mPagerAdapter.getCount() - 1);
        mTabs.setViewPager(mVpContent);
        mVpContent.setCurrentItem(mIndex, false);
    }
}
