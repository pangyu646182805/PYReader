package com.neuroandroid.pyreader.ui.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.bean.TextSelectBean;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.RankingList;
import com.neuroandroid.pyreader.mvp.contract.IRankingContract;
import com.neuroandroid.pyreader.mvp.presenter.RankingPresenter;
import com.neuroandroid.pyreader.ui.activity.MainActivity;
import com.neuroandroid.pyreader.utils.FragmentUtils;
import com.neuroandroid.pyreader.utils.RankingUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/6/28.
 */

public class RankingFragment extends BaseFragment<IRankingContract.Presenter>
        implements MainActivity.MainActivityFragmentCallbacks, IRankingContract.View {
    private static final int WEEK_RANKING = 10;
    private static final int MONTH_RANKING = 11;
    private static final int TOTAL_RANKING = 12;

    @BindView(R.id.rv_male)
    RecyclerView mRvMale;
    @BindView(R.id.rv_ranking)
    RecyclerView mRvRanking;
    @BindView(R.id.rv_ranking_list)
    RecyclerView mRvRankingList;

    private List<TextSelectBean> mMaleDataList = new ArrayList<>();
    private List<TextSelectBean> mRankingDataList = new ArrayList<>();
    private MaleAdapter mMaleAdapter;
    private RankingAdapter mRankingAdapter;
    private RankingListAdapter mRankingListAdapter;

    private int mCurrentGender = Constant.MALE_INT;
    private List<RankingList.MaleBean> mMaleBeanList;
    private List<RankingList.MaleBean> mFemaleBeanList;

    private RecyclerView mRvOtherRankingList;
    private ImageView mIvArrow;
    private LinearLayout mLlOtherRankingList;
    private RankingListAdapter mOtherRankingListAdapter;

    private RankingBookListFragment mBookListFragment;

    private int mRvRankingWidth;
    private int mCurrentRanking = WEEK_RANKING;

    @Override
    protected void initPresenter() {
        mPresenter = new RankingPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(UIUtils.getString(R.string.ranking));

        mRvMale.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvRanking.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvRankingList.setLayoutManager(new LinearLayoutManager(mContext));
        generateTextSelectBean();

        mMaleAdapter = new MaleAdapter(mContext, mMaleDataList, R.layout.item_ranking_tab);
        mMaleAdapter.clearRvAnim(mRvMale);
        mRvMale.setAdapter(mMaleAdapter);

        mRankingAdapter = new RankingAdapter(mContext, mRankingDataList, R.layout.item_ranking_tab);
        mRankingAdapter.clearRvAnim(mRvRanking);
        mRvRanking.setAdapter(mRankingAdapter);

        mRankingListAdapter = new RankingListAdapter(mContext, null, R.layout.item_ranking_list);
        mRankingListAdapter.clearRvAnim(mRvRankingList);
        mRvRankingList.setAdapter(mRankingListAdapter);
        addOtherRankingListFooter();

        mBookListFragment = new RankingBookListFragment();
        FragmentUtils.replaceFragment(getChildFragmentManager(), mBookListFragment, R.id.fl_container, false);
    }

    @Override
    protected void initData() {
        mPresenter.getRanking();
    }

    @Override
    protected void initListener() {
        mMaleAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<TextSelectBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, TextSelectBean textSelectBean) {
                if (isSelected) {
                    if (Constant.MALE_TEXT.equals(textSelectBean.getText())) {
                        mCurrentGender = Constant.MALE_INT;
                    } else {
                        mCurrentGender = Constant.FEMALE_INT;
                    }
                    replaceRankingList();
                    // 其他榜已经展开了才去模拟其他榜按钮点击事件
                    if (mOtherRankingListAdapter.getItemCount() != 0) {
                        mOtherRankingListAdapter.clearSelected();
                        mLlOtherRankingList.performClick();
                    }
                    requestRanking(isMale() ? mMaleBeanList.get(0).getRankingId() : mFemaleBeanList.get(0).getRankingId());
                    hideOrShowRvRanking(true);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mRankingAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<TextSelectBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, TextSelectBean textSelectBean) {
                if (isSelected) {
                    if (Constant.WEEK_RANKING.equals(textSelectBean.getText())) {
                        mCurrentRanking = WEEK_RANKING;
                    } else if (Constant.MONTH_RANKING.equals(textSelectBean.getText())) {
                        mCurrentRanking = MONTH_RANKING;
                    } else {
                        mCurrentRanking = TOTAL_RANKING;
                    }
                    int checkedPos = mRankingListAdapter.getCheckedPos();
                    RankingList.MaleBean maleBean = isMale() ? mMaleBeanList.get(checkedPos) : mFemaleBeanList.get(checkedPos);
                    String rankingId = maleBean.getRankingId();
                    switch (mCurrentRanking) {
                        case MONTH_RANKING:
                            rankingId = maleBean.getMonthRank();
                            break;
                        case TOTAL_RANKING:
                            rankingId = maleBean.getTotalRank();
                            break;
                    }
                    requestRanking(rankingId);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mRankingListAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<RankingList.MaleBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, RankingList.MaleBean maleBean) {
                if (isSelected) {
                    if (mOtherRankingListAdapter != null) {
                        mOtherRankingListAdapter.clearSelected();
                        mOtherRankingListAdapter.notifyDataSetChanged();
                    }
                    String rankingId = maleBean.getRankingId();
                    switch (mCurrentRanking) {
                        case MONTH_RANKING:
                            rankingId = maleBean.getMonthRank();
                            break;
                        case TOTAL_RANKING:
                            rankingId = maleBean.getTotalRank();
                            break;
                    }
                    requestRanking(rankingId);
                    hideOrShowRvRanking(true);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addOtherRankingListFooter() {
        View otherRankingListFooter = LayoutInflater.from(mContext).inflate(R.layout.layout_ranking_list_footer, mRvRankingList, false);
        mRankingListAdapter.addFooterView(otherRankingListFooter);
        mRvOtherRankingList = (RecyclerView) otherRankingListFooter.findViewById(R.id.rv_other_ranking_list);
        mIvArrow = (ImageView) otherRankingListFooter.findViewById(R.id.iv_arrow);
        mLlOtherRankingList = (LinearLayout) otherRankingListFooter.findViewById(R.id.ll_other_ranking_list);
        mRvOtherRankingList.setLayoutManager(new LinearLayoutManager(mContext));
        mOtherRankingListAdapter = new RankingListAdapter(mContext, null, R.layout.item_ranking_list);
        mOtherRankingListAdapter.clearRvAnim(mRvOtherRankingList);
        mRvOtherRankingList.setAdapter(mOtherRankingListAdapter);
        mOtherRankingListAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<RankingList.MaleBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, RankingList.MaleBean maleBean) {
                if (isSelected) {
                    requestRanking(maleBean.getRankingId());
                    mRankingListAdapter.clearSelected();
                    mRankingListAdapter.notifyDataSetChanged();
                    hideOrShowRvRanking(false);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mLlOtherRankingList.setOnClickListener(view -> {
            if (mOtherRankingListAdapter.getItemCount() == 0) {
                replaceOtherRankingList();
                ViewCompat.animate(mIvArrow).rotation(270).setDuration(200).start();
            } else {
                clearOtherRankingList();
                ViewCompat.animate(mIvArrow).rotation(90).setDuration(200).start();
            }
        });
    }

    private void generateTextSelectBean() {
        String[] rankingTabs = UIUtils.getStringArr(R.array.ranking_tabs);
        for (int i = 0; i < rankingTabs.length; i++) {
            mRankingDataList.add(new TextSelectBean(rankingTabs[i], i == 0));
        }
        String[] genderTabs = UIUtils.getStringArr(R.array.gender_tabs);
        for (int i = 0; i < genderTabs.length; i++) {
            mMaleDataList.add(new TextSelectBean(genderTabs[i], i == 0));
        }
    }

    @Override
    public boolean handleBackPress() {
        return false;
    }

    @Override
    public void showRanking(RankingList rankingList) {
        rankingList = RankingUtils.replaceRankingWord(rankingList);
        mMaleBeanList = rankingList.getMale();
        mFemaleBeanList = rankingList.getFemale();
        replaceRankingList();
        if (isMale())
            requestRanking(mMaleBeanList.get(0).getRankingId());
        else
            requestRanking(mFemaleBeanList.get(0).getRankingId());
    }

    /**
     * 截取MaleBeanList
     */
    private void replaceRankingList() {
        for (int i = 0; i < 5; i++) {
            mMaleBeanList.get(i).setSelected(i == 0);
            mFemaleBeanList.get(i).setSelected(i == 0);
        }
        mRankingListAdapter.setCheckedPos(0);
        mRankingListAdapter.replaceAll(isMale() ?
                mMaleBeanList.subList(0, 5) : mFemaleBeanList.subList(0, 5));
    }

    /**
     * 替换其他榜的数据
     */
    private void replaceOtherRankingList() {
        mOtherRankingListAdapter.replaceAll(isMale() ?
                mMaleBeanList.subList(5, mMaleBeanList.size()) : mFemaleBeanList.subList(5, mFemaleBeanList.size()));
    }

    /**
     * 清除其他榜的数据
     */
    private void clearOtherRankingList() {
        mOtherRankingListAdapter.clear();
    }

    /**
     * 当前选项是否是男生
     */
    private boolean isMale() {
        return mCurrentGender == Constant.MALE_INT;
    }

    /**
     * 请求排行榜书籍列表
     */
    private void requestRanking(String rankingId) {
        mBookListFragment.setRankingIdAndRequestRanking(rankingId);
    }

    /**
     * 隐藏或显示周/月/总榜
     */
    private void hideOrShowRvRanking(boolean show) {
        if (mRvRankingWidth == 0) mRvRankingWidth = mRvRanking.getWidth();
        if (show) {  // 如果已经显示则不执行显示动画
            if (mRvRanking.getAlpha() == 1) return;
        } else {  // 如果已经隐藏则不执行隐藏动画
            if (mRvRanking.getAlpha() == 0) return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(show ? 0 : 1.0f, show ? 1.0f : 0);
        animator.addUpdateListener(valueAnimator -> {
            float percent = (float) valueAnimator.getAnimatedValue();
            mRvRanking.setAlpha(percent);
            mRvRanking.getLayoutParams().width = (int) (mRvRankingWidth * percent);
            mRvRanking.requestLayout();
        });
        animator.setDuration(400).start();
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
    }

    @Override
    public void onDestroyView() {
        FragmentUtils.removeFragment(mBookListFragment);
        mBookListFragment = null;
        super.onDestroyView();
    }

    private class MaleAdapter extends SelectAdapter<TextSelectBean> {
        public MaleAdapter(Context context, List<TextSelectBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, TextSelectBean item, int position, int viewType) {
            NoPaddingTextView tvTabTitle = holder.getView(com.neuroandroid.pyreader.R.id.tv_tab_title);
            tvTabTitle.setBackgroundResource(item.isSelected() ? R.drawable.shape_ranking_tab_selected : R.drawable.shape_ranking_tab);
            tvTabTitle.setActivated(item.isSelected());
            tvTabTitle.setText(item.getText());
        }
    }

    private class RankingAdapter extends SelectAdapter<TextSelectBean> {
        public RankingAdapter(Context context, List<TextSelectBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, TextSelectBean item, int position, int viewType) {
            NoPaddingTextView tvTabTitle = holder.getView(com.neuroandroid.pyreader.R.id.tv_tab_title);
            tvTabTitle.setBackgroundResource(item.isSelected() ? R.drawable.shape_ranking_tab_selected : com.neuroandroid.pyreader.R.drawable.shape_ranking_tab);
            tvTabTitle.setActivated(item.isSelected());
            tvTabTitle.setText(item.getText());
        }
    }

    private class RankingListAdapter extends SelectAdapter<RankingList.MaleBean> {
        public RankingListAdapter(Context context, List<RankingList.MaleBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, RankingList.MaleBean item, int position, int viewType) {
            holder.setTextColor(R.id.tv_title, item.isSelected() ? UIUtils.getColor(R.color.colorPrimary) :
                    UIUtils.getColor(R.color.colorGray333))
                    .setBackgroundColor(R.id.ll_container, item.isSelected() ? UIUtils.getColor(R.color.backgroundPanel) :
                            UIUtils.getColor(R.color.colorTabItem))
                    .setText(R.id.tv_title, item.getTitle());
        }
    }
}
