package com.neuroandroid.pyreader.adapter.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.neuroandroid.pyreader.interfaces.OnItemClickListener;
import com.neuroandroid.pyreader.interfaces.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements CURD<T> {
    protected Context mContext;
    // 数据源
    private List<T> mDataList;
    private int mLayoutId;
    // 存放头部布局的容器
    protected SparseArray<View> mHeaderViews;
    // 存放底部布局的容器
    protected SparseArray<View> mFooterViews;
    // 行布局点击监听
    protected OnItemClickListener<T> mOnItemClickListener;
    // 行布局长按监听
    protected OnItemLongClickListener<T> mOnItemLongClickListener;
    // 空数据占位View
    protected View mEmptyView;
    @LayoutRes  // 空数据占位View的布局id
    protected int mEmptyViewId;
    // EmptyView的位置
    protected int mEmptyViewPosition = -1;
    protected IMultiItemViewType<T> mMultiItemViewType;
    protected RecyclerView.LayoutManager mLayoutManager;

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public BaseRvAdapter(Context context, List<T> dataList, int layoutId) {
        mContext = context;
        mDataList = dataList == null ? new ArrayList<>() : dataList;
        mLayoutId = layoutId;
        this.mMultiItemViewType = null;
    }

    public BaseRvAdapter(Context context, List<T> dataList, IMultiItemViewType<T> multiItemViewType) {
        mContext = context;
        mDataList = dataList == null ? new ArrayList<>() : dataList;
        this.mMultiItemViewType = multiItemViewType == null ? provideMultiItemViewType() : multiItemViewType;
    }

    public void clearRvAnim(RecyclerView rv) {
        if (rv == null) return;
        RecyclerView.ItemAnimator animator = rv.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        rv.getItemAnimator().setChangeDuration(333);
        rv.getItemAnimator().setMoveDuration(333);
    }

    /**
     * 如果不想要构造方法传入也可以让子类实现此方法
     */
    protected IMultiItemViewType<T> provideMultiItemViewType() {
        return null;
    }

    /**
     * 单击监听
     */
    public void setOnItemClickListener(OnItemClickListener<T> clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    /**
     * 长按监听
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> longClickListener) {
        this.mOnItemLongClickListener = longClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder;
        if (viewType >= BaseViewType.FOOTER && mFooterViews != null && mFooterViews.get(viewType) != null) {
            viewHolder = BaseViewHolder.createViewHolder(mContext, mFooterViews.get(viewType));
            return viewHolder;
        } else if (viewType >= BaseViewType.HEADER && mHeaderViews != null && mHeaderViews.get(viewType) != null) {
            viewHolder = BaseViewHolder.createViewHolder(mContext, mHeaderViews.get(viewType));
            return viewHolder;
        } else {
            int layoutId;
            if (mMultiItemViewType != null) {
                layoutId = mMultiItemViewType.getLayoutId(viewType);
            } else {
                layoutId = mLayoutId;
            }
            viewHolder = BaseViewHolder.createViewHolder(mContext, parent, layoutId);
            setListener(viewHolder);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isInHeadViewPos(position) || isInFootViewPos(position)) {
            return;
        }
        convert(holder, mDataList.get(position - getHeaderCounts()), position, getItemViewType(position));
    }

    /**
     * 设置空数据占位VIew
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    /**
     * 设置空数据占位View的id
     */
    public void setEmptyView(int layoutId) {
        this.mEmptyViewId = layoutId;
    }

    public abstract void convert(BaseViewHolder holder, T item, int position, int viewType);

    @Override
    public int getItemViewType(int position) {
        if (isInHeadViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isInFootViewPos(position)) {
            return mFooterViews.keyAt(position - getDataListSize() - getHeaderCounts());
        } else {
            if (mMultiItemViewType != null) {
                int newPosition = position - getHeaderCounts();
                return mMultiItemViewType.getItemViewType(newPosition, mDataList.get(newPosition));
            } else {
                return super.getItemViewType(position);
            }
        }
    }

    /**
     * 获取数据集数量
     */
    public int getDataListSize() {
        return mDataList.size();
    }

    /**
     * 某个位置是否处于HeadView的位置内
     */
    protected boolean isInHeadViewPos(int pos) {
        return pos < getHeaderCounts();
    }

    /**
     * 某个位置是否处于FootView的位置内
     */
    protected boolean isInFootViewPos(int pos) {
        return pos >= getDataListSize() + getHeaderCounts() &&
                pos < getDataListSize() + getHeaderCounts() + getFooterCounts();
    }

    /**
     * 获取HeadView的数量
     */
    public int getHeaderCounts() {
        return mHeaderViews != null ? mHeaderViews.size() : 0;
    }

    /**
     * 获取FootView的数量
     */
    public int getFooterCounts() {
        return mFooterViews != null ? mFooterViews.size() : 0;
    }

    /**
     * 设置布局点击监听[单机和长按]
     */
    protected void setListener(final BaseViewHolder viewHolder) {
        viewHolder.getItemView().setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                int position = viewHolder.getLayoutPosition();
                mOnItemClickListener.onItemClick(viewHolder, position, getItem(position));
            }
        });

        viewHolder.getItemView().setOnLongClickListener(view -> {
            if (mOnItemLongClickListener != null) {
                int position = viewHolder.getLayoutPosition();
                mOnItemLongClickListener.onItemLongClick(viewHolder, position, getItem(position));
            }
            return true;
        });
    }

    /**
     * 添加HeaderView
     * 必须在setLayoutManager()方法之后设置
     */
    public void addHeaderView(View... headerViews) {
        if (mHeaderViews == null)
            mHeaderViews = new SparseArray<>();
        for (View headerView : headerViews) {
            mHeaderViews.put(BaseViewType.HEADER + getHeaderCounts(), headerView);
        }
        notifyItemInserted(headerViews.length);
    }

    /**
     * 删除指定位置的HeaderView
     */
    public void removeHeaderViewAt(int index) {
        if (mHeaderViews != null) {
            mHeaderViews.removeAt(index);
            notifyItemRemoved(index);
        }
    }

    /**
     * 清空HeaderView
     */
    public void clearHeaderViews() {
        if (mHeaderViews != null && mHeaderViews.size() > 0) {
            int size = mHeaderViews.size();
            mHeaderViews.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * 添加FooterView
     * 必须在setLayoutManager()方法之后设置
     */
    public void addFooterView(View... footerViews) {
        if (mFooterViews == null)
            mFooterViews = new SparseArray<>();
        for (View footerView : footerViews) {
            mFooterViews.put(BaseViewType.FOOTER + getFooterCounts(), footerView);
        }
        notifyItemInserted(footerViews.length);
    }

    /**
     * 删除指定位置的HeaderView
     */
    public void removeFooterViewAt(int index) {
        if (mFooterViews != null) {
            mFooterViews.removeAt(index);
            notifyItemRemoved(index);
        }
    }

    /**
     * 清空HeaderView
     */
    public void clearFooterViews() {
        if (mFooterViews != null && mFooterViews.size() > 0) {
            int size = mFooterViews.size();
            mFooterViews.clear();
            notifyItemRangeRemoved(getHeaderCounts() + getDataListSize(), size);
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        } else {
            return getDataListSize() + getHeaderCounts() + getFooterCounts();
        }
    }

    /**
     * 获取数据源
     */
    public List<T> getDataList() {
        return mDataList;
    }

    public T getItem(int position) {
        position = position - getHeaderCounts();
        if (position > getDataListSize() - 1) {
            return null;
        }
        return getDataList().get(position);
    }

    @Override
    public void add(T item) {
        add(getHeaderCounts() + getDataListSize(), item);
    }

    @Override
    public void add(int position, T item) {
        if (item != null) {
            mDataList.add(position - getHeaderCounts(), item);
            notifyItemInserted(position);
        }
    }

    @Override
    public void addAll(List<T> items) {
        addAll(getHeaderCounts() + getDataListSize(), items);
    }

    @Override
    public void addAll(int position, List<T> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        if (position < 0 || position > getHeaderCounts() + getDataListSize()) {
            return;
        }
        mDataList.addAll(position - getHeaderCounts(), items);
        notifyItemRangeInserted(position - getHeaderCounts(), items.size());
    }

    @Override
    public void remove(T item) {
        int position = mDataList.indexOf(item);
        if (mDataList.remove(item)) {
            position = position + getHeaderCounts();
            notifyItemRemoved(position);
        }
    }

    @Override
    public void remove(int position) {
        mDataList.remove(position - getHeaderCounts());
        notifyItemRemoved(position);
    }

    @Override
    public void removeAll(List<T> items) {
        mDataList.removeAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void retainAll(List<T> items) {
        mDataList.retainAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void set(T oldItem, T newItem) {
        int index = mDataList.indexOf(oldItem);
        if (index != -1) {
            set(index + getHeaderCounts(), newItem);
        }
    }

    @Override
    public void set(int position, T item) {
        mDataList.set(position - getHeaderCounts(), item);
        notifyItemChanged(position);
    }

    @Override
    public void replaceAll(List<T> items) {
        if (mDataList == items) {
            notifyDataSetChanged();
            return;
        }
        if (items == null || items.isEmpty()) {
            clear();
            return;
        }
        mDataList.clear();
        mDataList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        int size = getDataListSize();
        if (size > 0) {
            mDataList.clear();
            notifyItemRangeRemoved(getHeaderCounts(), size);
        }
    }
}
