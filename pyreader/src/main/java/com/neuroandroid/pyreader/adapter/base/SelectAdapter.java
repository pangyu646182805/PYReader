package com.neuroandroid.pyreader.adapter.base;

import android.content.Context;
import android.view.View;

import com.neuroandroid.pyreader.annotation.SelectMode;

import java.util.HashSet;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/15.
 */

public abstract class SelectAdapter<T extends ISelect> extends BaseRvAdapter<T> {
    /**
     * 默认是单选模式
     */
    private int mCurrentSelectMode = ISelect.SINGLE_MODE;
    /**
     * 之前选中的位置
     */
    private int mPrePos;
    /**
     * 是否是选择模式
     */
    public boolean isSelectMode = true;
    /**
     * 长按能否触发选择模式
     */
    private boolean longTouchEnable = true;
    /**
     * 选择模式下的监听器
     */
    private OnItemSelectedListener<T> mItemSelectedListener;
    private final HashSet<T> selectedBeans = new HashSet<>();

    public void setItemSelectedListener(OnItemSelectedListener<T> itemSelectedListener) {
        mItemSelectedListener = itemSelectedListener;
    }

    public void setCheckedPos(int prePos) {
        mPrePos = prePos;
    }

    public int getCheckedPos() {
        return mPrePos;
    }

    @Override
    public void addHeaderView(View... headerViews) {
        super.addHeaderView(headerViews);
        setCheckedPos(headerViews.length);
    }

    public boolean isSelectMode() {
        return isSelectMode;
    }

    public SelectAdapter(Context context, List<T> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    public SelectAdapter(Context context, List<T> dataList, IMultiItemViewType<T> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    /**
     * 清除选择
     */
    public void clearSelected() {
        for (ISelect bean : getDataList()) {
            if (bean.isSelected())
                bean.setSelected(false);
        }
        selectedBeans.clear();
    }

    /**
     * 全选
     */
    public void selectAll() {
        selectedBeans.clear();
        for (ISelect bean : getDataList()) {
            selectedBeans.add((T) bean);
            if (!bean.isSelected())
                bean.setSelected(true);
        }
    }

    public void setSelectedMode(@SelectMode int mode) {
        mCurrentSelectMode = mode;
    }

    public void longTouchSelectModeEnable(boolean longTouchSelectModeEnable) {
        longTouchEnable = longTouchSelectModeEnable;
    }

    /**
     * 更新选择模式
     */
    public void updateSelectMode(boolean isSelect) {
        updateSelectMode(isSelect, -1);
    }

    /**
     * 更新选择模式
     * 如果position不等于-1则勾选position
     */
    public void updateSelectMode(boolean isSelect, int position) {
        if (isSelectMode != isSelect) {
            isSelectMode = isSelect;
            clearSelected();
            if (position != -1) {
                getItem(position).setSelected(true);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener(BaseViewHolder viewHolder) {
        viewHolder.getItemView().setOnClickListener(view ->
                performClick(viewHolder, viewHolder.getLayoutPosition()));

        viewHolder.getItemView().setOnLongClickListener(view -> {
            performLongClick(viewHolder, viewHolder.getLayoutPosition());
            return true;
        });
    }

    public void performClick(BaseViewHolder viewHolder, int position) {
        // 需要减去Header布局的数量
        final T bean = getDataList().get(position - getHeaderCounts());
        if (isSelectMode) {  // 如果是选择模式
            handleClickEvent(viewHolder, position, bean);
        } else {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(viewHolder, position, bean);
            }
        }
    }

    private void performLongClick(BaseViewHolder viewHolder, int position) {
        // 需要减去Header布局的数量
        final T bean = getDataList().get(position - getHeaderCounts());
        if (longTouchEnable) {
            handleClickEvent(viewHolder, position, bean);
        } else {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(viewHolder, position, bean);
            }
        }
    }

    private void handleClickEvent(BaseViewHolder viewHolder, int position, T bean) {
        // 在dataList集合中的position
        int realPosition = position - getHeaderCounts();
        if (mCurrentSelectMode == ISelect.SINGLE_MODE && bean.isSelected()) {
            if (mItemSelectedListener != null) {
                mItemSelectedListener.onItemSelected(viewHolder, realPosition, true, bean);
            }
            return;
        }
        boolean selected = !bean.isSelected();
        bean.setSelected(selected);
        dispatchSelected(viewHolder, realPosition, bean, selected);
        if (mCurrentSelectMode == ISelect.SINGLE_MODE && position != mPrePos && bean.isSelected()) {
            getDataList().get(mPrePos - getHeaderCounts()).setSelected(false);
            dispatchSelected(viewHolder, mPrePos, bean, false);
            notifyItemChanged(mPrePos);
        }
        notifyItemRangeChanged(position, 1);
        mPrePos = position;
    }

    private void dispatchSelected(BaseViewHolder viewHolder, int position, T bean, boolean isSelected) {
        if (isSelected) {
            selectedBeans.add(bean);
        } else {
            selectedBeans.remove(bean);
            if (mItemSelectedListener != null && selectedBeans.isEmpty()) {
                mItemSelectedListener.onNothingSelected();
            }
        }
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onItemSelected(viewHolder, position, isSelected, bean);
        }
    }

    public interface OnItemSelectedListener<T> {
        void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, T t);

        void onNothingSelected();
    }
}
