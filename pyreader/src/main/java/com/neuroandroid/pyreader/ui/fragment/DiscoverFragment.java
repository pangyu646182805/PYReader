package com.neuroandroid.pyreader.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.bean.MenuItem;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/19.
 */

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.rv_discover)
    RecyclerView mRvDiscover;

    private List<MenuItem> mMenuItemList = Arrays.asList(new MenuItem(UIUtils.getString(R.string.ranking)),
            new MenuItem(UIUtils.getString(R.string.topic)), new MenuItem(UIUtils.getString(R.string.category)));
    private DiscoverAdapter mDiscoverAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        mRvDiscover.setLayoutManager(new LinearLayoutManager(mContext));
        mRvDiscover.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mDiscoverAdapter = new DiscoverAdapter(mContext, mMenuItemList, R.layout.item_menu);
        mRvDiscover.setAdapter(mDiscoverAdapter);
    }

    @Override
    protected void initListener() {
        mDiscoverAdapter.setOnItemClickListener((holder, position, item) -> {
            MainActivity mainActivity = (MainActivity) mActivity;
            switch (position) {
                case 0:  // ranking
                    mainActivity.openRankingFragment();
                    break;
                case 1:  // topic
                    mainActivity.openTopicFragment();
                    break;
                case 2:  // category
                default:
                    mainActivity.openCategoryFragment();
                    break;
            }
        });
    }

    class DiscoverAdapter extends BaseRvAdapter<MenuItem> {
        public DiscoverAdapter(Context context, List<MenuItem> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, MenuItem item, int position, int viewType) {
            holder.setText(R.id.tv_title, item.getText());
        }
    }
}
