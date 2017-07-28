package com.neuroandroid.pyreader.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.utils.SystemUtils;
import com.neuroandroid.pyreader.widget.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/20.
 * 章节列表Fragment
 */
public class ChapterListFragment extends BaseFragment {
    public static final int CATALOG_POSITION = 0;
    public static final int BOOK_MARKS_POSITION = 1;

    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    private String[] mTitles = new String[] {"目录", "书签"};
    private List<BaseFragment> mFragments = new ArrayList<>();
    private boolean mImmersive;

    private OnDrawerPageChangeListener mDrawerPageChangeListener;
    private CatalogFragment mCatalogFragment;
    private BookmarksFragment mBookmarksFragment;

    public void setChaptersList(List<BookMixAToc.MixToc.Chapters> chaptersList) {
        mCatalogFragment.setChaptersList(chaptersList);
    }

    public void setImmersive(boolean immersive) {
        mImmersive = immersive;
        if (mImmersive) {
            getStatusBar().getLayoutParams().height = SystemUtils.getStatusHeight(mActivity);
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_chapter_list;
    }

    @Override
    protected void initView() {
        mDrawerPageChangeListener = (OnDrawerPageChangeListener) mActivity;
        mCatalogFragment = new CatalogFragment();
        mFragments.add(mCatalogFragment);
        mBookmarksFragment = new BookmarksFragment();
        mFragments.add(mBookmarksFragment);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        };
        mVpContent.setAdapter(pagerAdapter);
        mTabs.setViewPager(mVpContent);
    }

    @Override
    protected void initListener() {
        mVpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == mFragments.size() - 1) {
                    mDrawerPageChangeListener.onPageSelected(true);
                } else {
                    mDrawerPageChangeListener.onPageSelected(false);
                }
            }
        });
    }

    /**
     * 设置当前是目录页面还是书签页面
     */
    public void setCurrentItem(int position) {
        mVpContent.setCurrentItem(position, false);
    }

    /**
     * 设置bookId
     */
    public void setBookId(String bookId) {
        mCatalogFragment.setBookId(bookId);
    }

    /**
     * 设置当前阅读到了第几章节
     */
    public void setReadChapter(int readChapter) {
        mCatalogFragment.setReadChapter(readChapter);
    }

    /**
     * onDrawerClosed()调用
     * 当侧滑面板关闭的时候目录重新定义为顺序
     */
    public void restoreOrder() {
        mCatalogFragment.restoreOrder();
    }

    /**
     * 监听侧边栏的页面选择。
     */
    public interface OnDrawerPageChangeListener {
        void onPageSelected(boolean isLast);
    }
}
