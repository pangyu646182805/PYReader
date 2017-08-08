package com.neuroandroid.pyreader.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.model.response.BookListTags;
import com.neuroandroid.pyreader.mvp.contract.ITopicBookListContract;
import com.neuroandroid.pyreader.mvp.presenter.TopicBookListPresenter;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.FragmentUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.dialog.TopicTagsDialog;
import com.neuroandroid.pyreader.widget.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class TopicBookListFragment extends BaseFragment<ITopicBookListContract.Presenter>
        implements MainActivity.MainActivityFragmentCallbacks, ITopicBookListContract.View {
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.btn_filter)
    FloatingActionButton mBtnFilter;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private List<String> mTitles;
    private BookListTags mBookListTags;

    private BaseFragment mCurrentFragment;
    private String mCurrentTag = "";

    @Override
    protected void initPresenter() {
        mPresenter = new TopicBookListPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_topic_book_list;
    }

    @Override
    protected void initView() {
        mRootView.setBackgroundColor(ThemeUtils.getBackgroundColor());

        setDisplayHomeAsUpEnabled();
        setToolbarTitle(UIUtils.getString(R.string.topic));
    }

    @Override
    protected void initData() {
        mTitles = Arrays.asList(UIUtils.getStringArr(R.array.topic_tabs));

        mFragments.add(TopicFragment.newInstance(0));
        mFragments.add(TopicFragment.newInstance(1));
        mFragments.add(TopicFragment.newInstance(2));

        mVpContent.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
                return mTitles.get(position);
            }
        });
        mVpContent.setOffscreenPageLimit(mFragments.size() - 1);
        mTabs.setViewPager(mVpContent);

        mPresenter.getBookListTags();
    }

    @Override
    protected void initListener() {
        mBtnFilter.setOnClickListener(view -> {
            if (mBookListTags != null) {
                new TopicTagsDialog(mContext).setBookListTags(mBookListTags, mCurrentTag)
                        .setOnTagClickListener((dialog, tag) -> {
                            mCurrentTag = tag;
                            for (BaseFragment fragment : mFragments) {
                                TopicFragment topicFragment = (TopicFragment) fragment;
                                topicFragment.reFilterLoadData(tag);
                            }
                            dialog.dismissDialog();
                        }).showDialog();
            }
        });
    }

    @Override
    public boolean handleBackPress() {
        if (mCurrentFragment != null) {
            ViewCompat.animate(mBtnFilter).translationY(0).setDuration(200).start();
            FragmentUtils.removeFragment(mCurrentFragment);
            mCurrentFragment = null;
            return true;
        }
        return false;
    }

    @Override
    public void showBookListTags(BookListTags bookListTags) {
        mBookListTags = bookListTags;
    }

    @Override
    public void showTip(String tip) {
        mBookListTags = null;
    }

    public void openRecommendBookListFragment(BookList.BookListsBean bookListsBean) {
        ViewCompat.animate(mBtnFilter).translationY(mBtnFilter.getHeight() + UIUtils.getDimen(R.dimen.y32)).setDuration(200).start();
        mCurrentFragment = new RecommendBookListDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RecommendBookListDetailFragment.BUNDLE_BEAN, bookListsBean);
        mCurrentFragment.setArguments(bundle);
        FragmentUtils.replaceFragment(getChildFragmentManager(), mCurrentFragment, R.id.fl_container, false);
    }
}
