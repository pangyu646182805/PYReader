package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.base.BaseResponse;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.manager.RecommendManager;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.BookList;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.model.response.RecommendBookList;
import com.neuroandroid.pyreader.ui.activity.BookDetailActivity;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.FormatUtils;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

import static com.neuroandroid.pyreader.utils.UIUtils.getString;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class BookDetailAdapter extends BaseRvAdapter<BaseResponse> {
    public static final int VIEW_TYPE_BOOK_DETAIL_HEADER = 0;
    public static final int VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW = 1;
    public static final int VIEW_TYPE_BOOK_DETAIL_COMMUNITY = 2;
    public static final int VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST = 3;

    private int mToolBarHeight;
    private int mBookDetailHeaderHeight = -1;
    private int mPostCount;
    private boolean mBookInRecommend;

    public int getBookDetailHeaderHeight() {
        return mBookDetailHeaderHeight;
    }

    public void setAppBarLayoutHeight(int appBarLayoutHeight) {
        mToolBarHeight = appBarLayoutHeight;
        notifyItemChanged(VIEW_TYPE_BOOK_DETAIL_HEADER);
    }

    public BookDetailAdapter(Context context, List<BaseResponse> dataList, IMultiItemViewType<BaseResponse> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, BaseResponse item, int position, int viewType) {
        int mainColor = ThemeUtils.getMainColor();
        int subColor = ThemeUtils.getSubColor();
        int splitColor = ThemeUtils.getSplitColor();
        View split;
        switch (viewType) {
            case VIEW_TYPE_BOOK_DETAIL_HEADER:
                LinearLayout llBookDetailHeader = holder.getView(R.id.ll_book_detail_header);
                llBookDetailHeader.setPadding(0, mToolBarHeight, 0, 0);

                split = holder.getView(R.id.view_split);
                split.setBackgroundColor(splitColor);

                if (item != null) {
                    BookDetail bookDetail = (BookDetail) item;
                    mPostCount = bookDetail.getPostCount();
                    getActivity(BookDetailActivity.class).setBookTitle(bookDetail.getTitle());
                    ImageView ivBookCover = holder.getView(R.id.iv_book_cover);
                    ImageView ivBlurCover = holder.getView(R.id.iv_blur_background_cover);
                    ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + bookDetail.getCover(), R.mipmap.cover_default, ivBookCover);
                    ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + bookDetail.getCover(), R.mipmap.cover_default, ivBlurCover);
                    holder.setText(R.id.tv_book_title, bookDetail.getTitle())
                            .setText(R.id.tv_book_author, bookDetail.getAuthor())
                            .setText(R.id.tv_book_minor_cate, bookDetail.getMinorCate())
                            .setText(R.id.tv_book_word_count, FormatUtils.formatWordCount(bookDetail.getWordCount()))
                            .setText(R.id.tv_book_update, FormatUtils.getDescriptionTimeFromDateString(bookDetail.getUpdated()))
                            .setText(R.id.tv_follower, String.valueOf(bookDetail.getLatelyFollower()))
                            .setText(R.id.tv_retention_ratio, UIUtils.isEmpty(bookDetail.getRetentionRatio()) ?
                                    "-" : String.format(getString(R.string.book_detail_retention_ratio),
                                    bookDetail.getRetentionRatio()))
                            .setText(R.id.tv_serialize_word_count, bookDetail.getSerializeWordCount() < 0 ? "-" : String.valueOf(bookDetail.getSerializeWordCount()));
                    FlowLayout tagLayout = holder.getView(R.id.tag_layout);
                    for (String tag : bookDetail.getTags()) {
                        tagLayout.addView(buildTagView(tag));
                    }
                    ExpandableTextView expandLayout = holder.getView(R.id.expand_layout);
                    expandLayout.setText(bookDetail.getLongIntro());

                    holder.setTextColor(R.id.expandable_text, mainColor);

                    if (mBookDetailHeaderHeight == -1) {
                        llBookDetailHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                llBookDetailHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                mBookDetailHeaderHeight = llBookDetailHeader.getHeight() - mToolBarHeight;
                            }
                        });
                    }

                    refreshCollectionButtonText(bookDetail, holder.getView(R.id.tv_collection));
                    holder.setOnClickListener(R.id.tv_collection, view -> {
                        if (mBookInRecommend) {
                            RecommendManager.getInstance().removeRecommend(bookDetail.getBookId());
                            ShowUtils.showToast(String.format(UIUtils.getString(
                                    R.string.book_detail_has_remove_the_book_shelf), bookDetail.getTitle()));
                            initCollectionButtonText(holder.getView(R.id.tv_collection), true);
                        } else {
                            Recommend.BooksBean book = bookDetail.generateRecommendBook();
                            RecommendManager.getInstance().addRecommend(book);
                            ShowUtils.showToast(String.format(UIUtils.getString(
                                    R.string.book_detail_has_joined_the_book_shelf), book.getTitle()));
                            initCollectionButtonText(holder.getView(R.id.tv_collection), false);
                        }
                    });
                    holder.setOnClickListener(R.id.tv_read, view -> {
                        BookDetailActivity bookDetailActivity = getActivity(BookDetailActivity.class);
                        boolean fromBookRead = bookDetailActivity.isFromBookRead();
                        if (fromBookRead) {
                            // 如果是从书籍阅读界面跳转而来则直接finish掉当前界面
                            bookDetailActivity.finish();
                        } else {
                            // NavigationUtils.goToBookReadPage();
                        }
                    });
                }
                break;
            case VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW:
                split = holder.getView(R.id.view_split);
                split.setBackgroundColor(splitColor);
                split = holder.getView(R.id.view_split_1);
                split.setBackgroundColor(splitColor);

                holder.setTextColor(R.id.tv_hot_review_title, mainColor);

                if (item != null) {
                    HotReview hotReview = (HotReview) item;
                    RecyclerView rvHotReview = holder.getView(R.id.rv_hot_review);
                    rvHotReview.setLayoutManager(new LinearLayoutManager(mContext));
                    rvHotReview.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
                    HotReviewAdapter hotReviewAdapter = new HotReviewAdapter(mContext, hotReview.getReviews(), R.layout.item_hot_review);
                    hotReviewAdapter.setShowReviewTime(false);
                    rvHotReview.setAdapter(hotReviewAdapter);
                    holder.setText(R.id.tv_all_review_count, String.format(UIUtils.getString(
                            R.string.book_detail_all_review_count), hotReview.getTotal()));
                    holder.setOnClickListener(R.id.tv_all_review_count, view ->
                            getActivity(BookDetailActivity.class).openBookDetailCommunityFragment(1));
                }
                break;
            case VIEW_TYPE_BOOK_DETAIL_COMMUNITY:
                ImageView ivArrow = holder.getView(R.id.iv_arrow);
                ivArrow.setColorFilter(mainColor);

                holder.setTextColor(R.id.tv_book_community, mainColor)
                        .setText(R.id.tv_book_posts_count, String.format(UIUtils.getString(
                                R.string.book_detail_post_count), mPostCount))
                .setTextColor(R.id.tv_book_posts_count, subColor);
                break;
            case VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST:
                split = holder.getView(R.id.view_split);
                split.setBackgroundColor(ThemeUtils.getSplitColor());

                holder.setTextColor(R.id.tv_recommend_title, mainColor);

                if (item != null) {
                    RecommendBookList recommendBookList = (RecommendBookList) item;
                    RecyclerView rvRecommendBookList = holder.getView(R.id.rv_recommend_booklist);
                    rvRecommendBookList.setLayoutManager(new LinearLayoutManager(mContext));
                    rvRecommendBookList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
                    RecommendBookListAdapter recommendBookListAdapter = new RecommendBookListAdapter(mContext, recommendBookList.getBooklists(), R.layout.item_recommend_booklist);
                    rvRecommendBookList.setAdapter(recommendBookListAdapter);
                    recommendBookListAdapter.setOnItemClickListener((holder1, position1, item1) -> {
                        BookList.BookListsBean bookListsBean = BookList.BookListsBean.generateBookListsBean(item1);

                        getActivity(BookDetailActivity.class).openRecommendBookListFragment(bookListsBean);
                    });
                }
                break;
        }
    }

    private void refreshCollectionButtonText(BookDetail bookDetail, TextView tvCollection) {
        if (RecommendManager.getInstance().bookInRecommend(bookDetail.getBookId())) {
            initCollectionButtonText(tvCollection, false);
        } else {
            initCollectionButtonText(tvCollection, true);
        }
    }

    private void initCollectionButtonText(TextView tvCollection, boolean collection) {
        if (collection) {
            tvCollection.setText("追更新");
            mBookInRecommend = false;
        } else {
            tvCollection.setText("不追了");
            mBookInRecommend = true;
        }
    }

    /**
     * 生成标签TextView
     */
    private NoPaddingTextView buildTagView(String tag) {
        NoPaddingTextView tvTag = new NoPaddingTextView(mContext);
        tvTag.setText(tag);
        tvTag.setTextColor(UIUtils.getColor(R.color.colorGray666));
        tvTag.setBackgroundResource(R.drawable.shape_book_detail_tag_selector);
        int padding = (int) UIUtils.getDimen(R.dimen.x8);
        tvTag.setPadding(padding * 2, padding, padding * 2, padding);
        tvTag.setOnClickListener(new TagClickListener(tag));
        return tvTag;
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
                switch (viewType) {
                    case VIEW_TYPE_BOOK_DETAIL_HEADER:
                        return R.layout.item_book_detail_header;
                    case VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW:
                        return R.layout.item_book_detail_hot_review;
                    case VIEW_TYPE_BOOK_DETAIL_COMMUNITY:
                        return R.layout.item_book_detail_community;
                    case VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST:
                    default:
                        return R.layout.item_book_detail_recommend_booklist;
                }
            }
        };
    }

    private class TagClickListener implements View.OnClickListener {
        private final String tag;

        public TagClickListener(String tag) {
            this.tag = tag;
        }

        @Override
        public void onClick(View view) {
            getActivity(BookDetailActivity.class).openBooksByTagFragment(tag);
        }
    }

    private class RecommendBookListAdapter extends BaseRvAdapter<RecommendBookList.BooklistsBean> {
        public RecommendBookListAdapter(Context context, List<RecommendBookList.BooklistsBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, RecommendBookList.BooklistsBean item, int position, int viewType) {
            ImageView ivBookCover = holder.getView(R.id.iv_book_cover);
            ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + item.getCover(), R.mipmap.cover_default, ivBookCover);

            int mainColor = ThemeUtils.getMainColor();
            int subColor = ThemeUtils.getSubColor();
            int threeLevelColor = ThemeUtils.getThreeLevelColor();

            holder.setText(R.id.tv_title, item.getTitle()).setTextColor(R.id.tv_title, mainColor)
                    .setText(R.id.tv_author, item.getAuthor()).setTextColor(R.id.tv_author, subColor)
                    .setText(R.id.tv_desc, item.getDesc()).setTextColor(R.id.tv_desc, subColor)
                    .setText(R.id.tv_recommend_count, String.format(getString(R.string
                            .book_detail_recommend_book_list_book_count), item.getBookCount()))
                    .setTextColor(R.id.tv_recommend_count, threeLevelColor)
                    .setText(R.id.tv_collect_count, String.format(getString(R.string
                            .book_detail_recommend_book_list_collect_count), item.getCollectorCount()))
                    .setTextColor(R.id.tv_collect_count, threeLevelColor);
        }
    }
}
