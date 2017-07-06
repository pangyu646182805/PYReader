package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.base.BaseResponse;
import com.neuroandroid.pyreader.bean.SearchHistoryBean;
import com.neuroandroid.pyreader.manager.CacheManager;
import com.neuroandroid.pyreader.model.response.HotWord;
import com.neuroandroid.pyreader.ui.fragment.SearchFragment;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by NeuroAndroid on 2017/6/26.
 */

public class SearchAdapter extends BaseRvAdapter<BaseResponse> {
    public static final int VIEW_TYPE_HOT_WORD = 0;
    public static final int VIEW_TYPE_SEARCH_HISTORY = 1;

    private SearchFragment mSearchFragment;
    private int mHotWordNum = 8;

    public void setSearchFragment(SearchFragment searchFragment) {
        mSearchFragment = searchFragment;
    }

    public SearchAdapter(Context context, List<BaseResponse> dataList, IMultiItemViewType<BaseResponse> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, BaseResponse item, int position, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HOT_WORD:
                if (item != null) {
                    HotWord hotWord = (HotWord) item;
                    FlowLayout tagLayout = holder.getView(R.id.tag_layout);
                    List<String> hotWords = hotWord.getHotWords();
                    showHotWord(tagLayout, hotWords);
                    holder.setOnClickListener(R.id.ll_refresh, view -> showHotWord(tagLayout, hotWords));
                }
                break;
            case VIEW_TYPE_SEARCH_HISTORY:
                RecyclerView rvSearchHistory = holder.getView(R.id.rv_search_history);
                if (item != null) {
                    rvSearchHistory.setVisibility(View.VISIBLE);
                    SearchHistoryBean historyBean = (SearchHistoryBean) item;
                    rvSearchHistory.setLayoutManager(new LinearLayoutManager(mContext));
                    SearchHistoryAdapter searchHistoryAdapter = new SearchHistoryAdapter(mContext, historyBean.getSearchHistoryList(), R.layout.item_search_history_child);
                    rvSearchHistory.setAdapter(searchHistoryAdapter);
                    searchHistoryAdapter.setOnItemClickListener((holder1, position1, item1) -> {
                        mSearchFragment.searchBooks(item1);
                    });
                    holder.setOnClickListener(R.id.iv_clear_history, view -> {
                        if (CacheManager.clearSearchHistory(mContext)) {
                            set(VIEW_TYPE_SEARCH_HISTORY, CacheManager.getSearchHistoryList(mContext));
                        }
                    });
                } else {
                    rvSearchHistory.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * 显示热搜书籍
     */
    private void showHotWord(FlowLayout tagLayout, List<String> hotWords) {
        randSelect(hotWords);
        tagLayout.removeAllViews();
        for (int i = 0; i < mHotWordNum; i++) {
            tagLayout.addView(buildTagView(hotWords.get(i)));
        }
    }

    @Override
    protected IMultiItemViewType<BaseResponse> provideMultiItemViewType() {
        return new IMultiItemViewType<BaseResponse>() {
            @Override
            public int getItemViewType(int position, BaseResponse baseResponse) {
                return position;
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == VIEW_TYPE_HOT_WORD) {
                    return R.layout.item_search_hot_word;
                } else {
                    return R.layout.item_search_history;
                }
            }
        };
    }

    /**
     * 从热搜书籍中随机选择8个
     */
    private void randSelect(List<String> tags) {
        Random rand = new Random();
        for (int i = 0; i < mHotWordNum; i++) {
            swap(tags, i, rand.nextInt(tags.size() - i) + i);
        }
    }

    public static void swap(List<String> tags, int m, int n) {
        String tag = tags.get(n);
        tags.set(n, tags.get(m));
        tags.set(m, tag);
    }

    /**
     * 生成标签TextView
     */
    private NoPaddingTextView buildTagView(String tag) {
        NoPaddingTextView tvTag = new NoPaddingTextView(mContext);
        tvTag.setText(tag);
        tvTag.setTextSize(12);
        tvTag.setTextColor(UIUtils.getColor(R.color.colorGray333));
        tvTag.setBackgroundResource(R.drawable.shape_book_detail_tag_selector);
        int padding = (int) UIUtils.getDimen(R.dimen.x8);
        tvTag.setPadding(padding * 2, padding, padding * 2, padding);
        tvTag.setOnClickListener(new TagClickListener(tag));
        return tvTag;
    }

    private class TagClickListener implements View.OnClickListener {
        private final String tag;

        public TagClickListener(String tag) {
            this.tag = tag;
        }

        @Override
        public void onClick(View view) {
            mSearchFragment.saveSearchHistory(tag);
            mSearchFragment.searchBooks(tag);
        }
    }

    private class SearchHistoryAdapter extends BaseRvAdapter<String> {
        public SearchHistoryAdapter(Context context, List<String> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, String item, int position, int viewType) {
            holder.setText(R.id.tv_search_history, item);
        }
    }
}
