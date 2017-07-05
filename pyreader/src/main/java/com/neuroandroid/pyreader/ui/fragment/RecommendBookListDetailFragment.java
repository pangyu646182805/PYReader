package com.neuroandroid.pyreader.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.RecommendBookListDetailAdapter;
import com.neuroandroid.pyreader.base.BaseFragment;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.model.response.BookListDetail;
import com.neuroandroid.pyreader.mvp.contract.IRecommendBookListDetailContract;
import com.neuroandroid.pyreader.mvp.presenter.RecommendBookListDetailPresenter;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.NavigationUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NeuroAndroid on 2017/6/27.
 * 推荐书单页面
 */
public class RecommendBookListDetailFragment extends BaseFragment<IRecommendBookListDetailContract.Presenter>
        implements IRecommendBookListDetailContract.View {
    public static final String BUNDLE_BEAN = "BookListBean";

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_book_list)
    RecyclerView mRvBookList;

    private BookList.BookListsBean mBookListsBean;
    private RecommendBookListDetailAdapter mBookListDetailAdapter;
    private HeaderViewHolder mHeaderViewHolder;

    @Override
    protected void initPresenter() {
        mPresenter = new RecommendBookListDetailPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_recommend_book_list;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(UIUtils.getString(R.string.book_list_detail));
        mRvBookList.setLayoutManager(new LinearLayoutManager(mContext));
        mBookListDetailAdapter = new RecommendBookListDetailAdapter(mContext, null, R.layout.item_recommend_booklist_detail);
        mRvBookList.setAdapter(mBookListDetailAdapter);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_booklist_detail_header, mRvBookList, false);
        mHeaderViewHolder = new HeaderViewHolder(headerView);
        mBookListDetailAdapter.addHeaderView(headerView);
    }

    @Override
    protected void initData() {
        mBookListsBean = (BookList.BookListsBean) getArguments().getSerializable(BUNDLE_BEAN);
        showLoading();
        mPresenter.getBookListDetail(mBookListsBean.getBookListId());
    }

    @Override
    protected void initListener() {
        mBookListDetailAdapter.setOnItemClickListener((holder, position, item) -> NavigationUtils.goToBookDetailPage(mActivity, item.getBook().getBookId()));
    }

    @Override
    public void showBookList(BookListDetail bookListDetail) {
        hideLoading();
        BookListDetail.BookListBean bookList = bookListDetail.getBookList();
        mHeaderViewHolder.mTvBookListTitle.setText(bookList.getTitle());
        mHeaderViewHolder.mTvBookListDesc.setText(bookList.getDesc());
        mHeaderViewHolder.mTvAuthor.setText(bookList.getAuthor().getNickname());
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + bookList.getAuthor().getAvatar(),
                Constant.MALE.equals(bookList.getGender()) ? R.mipmap.ic_male : R.mipmap.ic_female, mHeaderViewHolder.mIvHead);

        mBookListDetailAdapter.replaceAll(bookList.getBooks());
    }

    @Override
    public void showTip(String tip) {
        hideLoading();
        showError(() -> {
            showLoading();
            mPresenter.getBookListDetail(mBookListsBean.getBookListId());
        });
    }

    public static class HeaderViewHolder {
        @BindView(R.id.tv_book_list_title)
        NoPaddingTextView mTvBookListTitle;
        @BindView(R.id.tv_book_list_desc)
        NoPaddingTextView mTvBookListDesc;
        @BindView(R.id.iv_head)
        ImageView mIvHead;
        @BindView(R.id.tv_author)
        NoPaddingTextView mTvAuthor;

        public HeaderViewHolder(View headerView) {
            ButterKnife.bind(this, headerView);
        }
    }
}
