package com.neuroandroid.pyreader.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.model.response.BookMixAToc;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/7/21.
 */

public class CatalogFragment extends BaseFragment {
    @BindView(R.id.fl_order)
    FrameLayout mFlOrder;
    @BindView(R.id.fl_jump_current)
    FrameLayout mFlJumpCurrent;
    @BindView(R.id.rv_catalog)
    FastScrollRecyclerView mRvCatalog;

    private List<BookMixAToc.MixToc.Chapters> mChaptersList;
    private CatalogAdapter mCatalogAdapter;

    public void setChaptersList(List<BookMixAToc.MixToc.Chapters> chaptersList) {
        mChaptersList = chaptersList;
        if (mCatalogAdapter != null) {
            if (mCatalogAdapter.getItemCount() <= 0) {
                mCatalogAdapter.replaceAll(mChaptersList);
            }
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_catalog;
    }

    @Override
    protected void initView() {
        mRvCatalog.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCatalog.addItemDecoration(DividerUtils.generateHorizontalDivider(mContext, R.dimen.y1, R.color.split));
        mCatalogAdapter = new CatalogAdapter(mContext, mChaptersList, R.layout.item_catalog);
        mCatalogAdapter.clearRvAnim(mRvCatalog);
        mCatalogAdapter.longTouchSelectModeEnable(false);
        mRvCatalog.setAdapter(mCatalogAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mFlOrder.setOnLongClickListener(view -> {
            ShowUtils.showToast("排序");
            return true;
        });
        mFlJumpCurrent.setOnLongClickListener(view -> {
            ShowUtils.showToast("跳转至当前章节");
            return true;
        });
    }

    private class CatalogAdapter extends SelectAdapter<BookMixAToc.MixToc.Chapters> {
        public CatalogAdapter(Context context, List<BookMixAToc.MixToc.Chapters> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, BookMixAToc.MixToc.Chapters item, int position, int viewType) {
            holder.setText(R.id.tv_catalog, item.getTitle())
            .setTextColor(R.id.tv_catalog, item.isSelected() ?
                    UIUtils.getColor(R.color.colorPrimary) : UIUtils.getColor(R.color.black));
        }
    }
}
