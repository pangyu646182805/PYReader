package com.neuroandroid.pyreader.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.annotation.SelectMode;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;

/**
 * Created by NeuroAndroid on 2017/6/16.
 */

public class ListDialog<ADAPTER extends SelectAdapter, DATA extends ISelect> extends PYDialog<ListDialog<ADAPTER, DATA>> {
    private RecyclerView mRvList;
    private ADAPTER mSelectAdapter;

    public ListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_list_dialog;
    }

    @Override
    protected void initView() {
        /**
         * 列表单选/多选模式
         * 默认没有标题栏和底部栏
         * 如果需要显示则调用
         * @see PYDialog#showTitle()
         * @see PYDialog#showButton()
         */
        setNoTitle();
        setNoButton();
        mRvList = getView(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));

        RecyclerView.ItemAnimator animator = mRvList.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        animator.setChangeDuration(333);
        animator.setMoveDuration(333);
    }

    /**
     * 设置(单选或者多选)模式的适配器
     * item高度自定义
     */
    public ListDialog setSelectAdapter(ADAPTER selectAdapter,
                                       SelectAdapter.OnItemSelectedListener<DATA> itemSelectedListener) {
        if (selectAdapter == null) {
            throw new IllegalArgumentException("selectAdapter is null");
        }
        mSelectAdapter = selectAdapter;
        if (itemSelectedListener != null) {
            mSelectAdapter.setItemSelectedListener(itemSelectedListener);
        }
        mRvList.setAdapter(mSelectAdapter);
        return this;
    }

    /**
     * 设置勾选的位置
     */
    public ListDialog setAdapterCheckedPos(int pos) {
        if (mSelectAdapter != null) {
            mSelectAdapter.setCheckedPos(pos);
        }
        return this;
    }

    /**
     * 设置选择模式(单选或者多选)
     * 默认单选
     *
     * @param mode {@link SelectMode}
     */
    public ListDialog setSelectMode(@SelectMode int mode) {
        if (mSelectAdapter == null) {
            throw new IllegalArgumentException("selectAdapter is null");
        } else {
            mSelectAdapter.setSelectedMode(mode);
        }
        return this;
    }
}
