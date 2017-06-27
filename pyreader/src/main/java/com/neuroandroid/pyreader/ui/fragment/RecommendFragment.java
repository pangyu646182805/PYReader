package com.neuroandroid.pyreader.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.RecommendAdapter;
import com.neuroandroid.pyreader.adapter.base.ISelect;
import com.neuroandroid.pyreader.base.BaseLazyFragment;
import com.neuroandroid.pyreader.event.BaseEvent;
import com.neuroandroid.pyreader.manager.RecommendManager;
import com.neuroandroid.pyreader.manager.SettingManager;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.mvp.contract.IRecommendContract;
import com.neuroandroid.pyreader.mvp.presenter.RecommendPresenter;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.NavigationUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.CustomRefreshHeader;
import com.neuroandroid.pyreader.widget.dialog.BookDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/19.
 */
public class RecommendFragment extends BaseLazyFragment<IRecommendContract.Presenter> implements IRecommendContract.View {
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    private RecommendAdapter mRecommendAdapter;

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
                                        NavigationUtils.goToBookDetailPage(mActivity, item.getBookId());
                                        break;
                                    case 1:

                                        break;
                                    case 2:

                                        break;
                                    case 3:

                                        break;
                                }
                            }
                        }).showDialog());
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
}
