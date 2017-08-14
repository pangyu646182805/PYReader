package com.neuroandroid.pyreader.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialcab.MaterialCab;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.RecommendAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.base.BaseLazyFragment;
import com.neuroandroid.pyreader.event.BaseEvent;
import com.neuroandroid.pyreader.manager.CacheManager;
import com.neuroandroid.pyreader.manager.RecommendManager;
import com.neuroandroid.pyreader.manager.SettingManager;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.contract.IRecommendContract;
import com.neuroandroid.pyreader.mvp.presenter.RecommendPresenter;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.NavigationUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.neuroandroid.pyreader.widget.dialog.BookDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/19.
 */
public class RecommendFragment extends BaseLazyFragment<IRecommendContract.Presenter> implements IRecommendContract.View, MaterialCab.Callback {
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    private RecommendAdapter mRecommendAdapter;
    private MaterialCab mCab;

    private List<Recommend.BooksBean> mSelectedBooks = new ArrayList<>();

    @Override
    protected void initPresenter() {
        mPresenter = new RecommendPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView() {
        mRvRecommend.setLayoutManager(new LinearLayoutManager(mContext));
        mRvRecommend.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mRecommendAdapter = new RecommendAdapter(mContext, null, R.layout.item_recomment);
        mRecommendAdapter.setSelectedMode(ISelect.MULTIPLE_MODE);
        mRecommendAdapter.updateSelectMode(false);
        mRecommendAdapter.longTouchSelectModeEnable(false);
        mRecommendAdapter.clearRvAnim(mRvRecommend);
        mRvRecommend.setAdapter(mRecommendAdapter);

        mRefreshLayout.setHeaderView(new CustomRefreshHeader(mContext));

        View footerView = LayoutInflater.from(mContext).inflate(R.layout.item_recomment_footer, mRvRecommend, false);
        ImageView ivAdd = (ImageView) footerView.findViewById(R.id.iv_add);
        ivAdd.setColorFilter(ThemeUtils.getMainColor());
        NoPaddingTextView tvAdd = (NoPaddingTextView) footerView.findViewById(R.id.tv_add);
        tvAdd.setTextColor(ThemeUtils.getMainColor());
        mRecommendAdapter.addFooterView(footerView);
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                UIUtils.getHandler().postDelayed(() -> {
                    List<Recommend.BooksBean> recommend = RecommendManager.getInstance().getRecommend();
                    if (mRecommendAdapter != null) mRecommendAdapter.replaceAll(recommend);
                    refreshLayout.finishRefreshing();
                }, 250);
            }

            @Override
            public void onFinishRefresh() {
                checkIsEmpty();
            }
        });
        mRecommendAdapter.setOnItemLongClickListener((holder, position, item) ->
                new BookDialog(mContext)
                        .setBookTitle(item.getTitle())
                        .setFromSDCard(item.isFromSD())
                        .setOnDialogItemClickListener((dialog, viewHolder, pos, str) -> {
                            dialog.dismissDialog();
                            if (dialog.isFromSDCard()) {  // 来自SD卡的书籍
                                switch (pos) {
                                    case 0:

                                        break;
                                    case 1:

                                        break;
                                    case 2:

                                        break;
                                }
                            } else {  // 来自网络的书籍
                                switch (pos) {
                                    case 0:  // 书籍详情
                                        NavigationUtils.goToBookDetailPage(mActivity, item.getBookId(), false);
                                        break;
                                    case 1:
                                        break;
                                    case 2:  // 删除
                                        List<Recommend.BooksBean> selectedBooks = new ArrayList<>();
                                        selectedBooks.add(mRecommendAdapter.getItem(position));
                                        batchManage(selectedBooks);
                                        break;
                                    case 3:  // 批量管理
                                        mCab = getActivity(MainActivity.class).openCab(R.menu.menu_book_manage, this);
                                        mRecommendAdapter.longTouchSelectModeEnable(true);
                                        break;
                                }
                            }
                        }).showDialog());
        mRecommendAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<Recommend.BooksBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, Recommend.BooksBean booksBean) {
                if (isSelected) {
                    mSelectedBooks.add(booksBean);
                } else {
                    mSelectedBooks.remove(booksBean);
                }
                setMaterialCabTitle();
                mCab.getMenu().findItem(R.id.action_delete).setEnabled(!mSelectedBooks.isEmpty());
                mCab.getMenu().findItem(R.id.action_select_all).setTitle(mSelectedBooks.size() == mRecommendAdapter.getDataListSize() ?
                        UIUtils.getString(R.string.un_select_all) : UIUtils.getString(R.string.select_all));
            }

            @Override
            public void onNothingSelected() {
                // closeMaterialCab();
            }
        });
        mRecommendAdapter.setOnItemClickListener((holder, position, item) ->
                NavigationUtils.goToBookReadPage(mActivity, item));
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mRecommendAdapter != null && mRecommendAdapter.getDataList().isEmpty()) {
                mRefreshLayout.startRefresh();
            }
        } else {
            mRefreshLayout.finishRefreshing();
        }
    }

    /**
     * 关闭MaterialCab
     */
    private void closeMaterialCab() {
        if (mCab != null && mCab.isActive()) mCab.finish();
    }

    /**
     * 设置MaterialCab的标题
     */
    private void setMaterialCabTitle() {
        if (mSelectedBooks.size() == 1) {
            mCab.setTitle(mSelectedBooks.get(0).getTitle());
        } else if (mSelectedBooks.size() > 1) {
            mCab.setTitle(String.format(UIUtils.getString(R.string.x_selected), mSelectedBooks.size()));
        }
    }

    /**
     * 检查RecyclerView列表是否为空
     */
    private void checkIsEmpty() {
        if (mRecommendAdapter == null || (mRecommendAdapter != null && mRecommendAdapter.getItemCount() == 0)) {
            showError(() -> showTip("tip"));
        } else {
            hideLoading();
        }
    }

    @Override
    public void showRecommendList(Recommend recommend) {
        L.e("json : " + new Gson().toJson(recommend));

        hideLoading();
        List<Recommend.BooksBean> books = recommend.getBooks();
        mRecommendAdapter.replaceAll(books);
        RecommendManager.getInstance().saveRecommend(books);
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {
        switch (event.getEventFlag()) {
            case BaseEvent.EVENT_CHOOSE_SEX:
                mPresenter.getRecommend(SettingManager.getChooseSex(mContext));
                break;
            case BaseEvent.EVENT_RECOMMEND:
                mRefreshLayout.startRefresh();
                break;
        }
    }

    @Override
    public boolean onCabCreated(MaterialCab cab, Menu menu) {
        mRefreshLayout.setEnableRefresh(false);
        mRecommendAdapter.updateSelectMode(true);
        cab.setTitle(UIUtils.getString(R.string.book_manage));
        menu.findItem(R.id.action_delete).setEnabled(!mSelectedBooks.isEmpty());
        return true;
    }

    @Override
    public boolean onCabItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                if (UIUtils.getString(R.string.select_all).equals(item.getTitle())) {
                    // 全选
                    item.setTitle(UIUtils.getString(R.string.un_select_all));
                    mRecommendAdapter.selectAll();
                    mSelectedBooks.clear();
                    mSelectedBooks.addAll(mRecommendAdapter.getDataList());
                    setMaterialCabTitle();
                } else {
                    // 取消全选
                    item.setTitle(UIUtils.getString(R.string.select_all));
                    mRecommendAdapter.clearSelected();
                    mCab.setTitle(UIUtils.getString(R.string.book_manage));
                    mSelectedBooks.clear();
                }
                mCab.getMenu().findItem(R.id.action_delete).setEnabled(!mSelectedBooks.isEmpty());
                mRecommendAdapter.notifyDataSetChanged();
                break;
            case R.id.action_delete:
                batchManage(mSelectedBooks);
                break;
        }
        return true;
    }

    private void batchManage(List<Recommend.BooksBean> selectedBooks) {
        final boolean[] selected = {true};
        new AlertDialog.Builder(mActivity)
                .setTitle(UIUtils.getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{UIUtils.getString(R.string.delete_local_cache)},
                        selected, (dialogInterface, which, isChecked) -> selected[0] = isChecked)
                .setPositiveButton("确定", (dialogInterface, which) ->
                        new BatchManageTask().execute(selectedBooks, selected[0]))
                .setNegativeButton("取消", null)
                .create().show();
    }

    @Override
    public boolean onCabFinished(MaterialCab cab) {
        mRecommendAdapter.longTouchSelectModeEnable(false);
        mRefreshLayout.setEnableRefresh(true);
        mRecommendAdapter.updateSelectMode(false);
        mSelectedBooks.clear();
        return true;
    }

    private class BatchManageTask extends AsyncTask<Object, Void, Void> {
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(Object... objects) {
            List<Recommend.BooksBean> selectedBooks = (List<Recommend.BooksBean>) objects[0];
            boolean delLocalCache = (boolean) objects[1];
            long start = System.currentTimeMillis();
            CacheManager.batchManage(mContext, selectedBooks, delLocalCache);
            L.e("time : " + (System.currentTimeMillis() - start));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
            mProgressDialog = null;
            closeMaterialCab();
        }
    }
}
