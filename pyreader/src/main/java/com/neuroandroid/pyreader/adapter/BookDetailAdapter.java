package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.base.BaseResponse;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;
import com.neuroandroid.pyreader.utils.DividerUtils;
import com.neuroandroid.pyreader.utils.FormatUtils;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.neuroandroid.pyreader.widget.PYRatingBar;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        switch (viewType) {
            case VIEW_TYPE_BOOK_DETAIL_HEADER:
                LinearLayout llBookDetailHeader = holder.getView(R.id.ll_book_detail_header);
                llBookDetailHeader.setPadding(0, mToolBarHeight, 0, 0);
                if (item != null) {
                    BookDetail bookDetail = (BookDetail) item;
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
                                    "-" : String.format(UIUtils.getString(R.string.book_detail_retention_ratio),
                                    bookDetail.getRetentionRatio()))
                            .setText(R.id.tv_serialize_word_count, bookDetail.getSerializeWordCount() < 0 ? "-" : String.valueOf(bookDetail.getSerializeWordCount()));
                    FlowLayout tagLayout = holder.getView(R.id.tag_layout);
                    for (String tag : bookDetail.getTags()) {
                        tagLayout.addView(buildTagView(tag));
                    }
                    ExpandableTextView expandLayout = holder.getView(R.id.expand_layout);
                    expandLayout.setText(bookDetail.getLongIntro());

                    if (mBookDetailHeaderHeight == -1) {
                        llBookDetailHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                llBookDetailHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                mBookDetailHeaderHeight = llBookDetailHeader.getHeight();
                            }
                        });
                    }
                }
                break;
            case VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW:
                if (item != null) {
                    HotReview hotReview = (HotReview) item;
                    RecyclerView rvHotReview = holder.getView(R.id.rv_hot_review);
                    rvHotReview.setLayoutManager(new LinearLayoutManager(mContext));
                    rvHotReview.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
                    rvHotReview.setAdapter(new HotReviewAdapter(mContext, hotReview.getReviews(), R.layout.item_hot_review));
                }
                break;
            case VIEW_TYPE_BOOK_DETAIL_COMMUNITY:
                break;
            case VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST:
                if (item != null) {
                    RecommendBookList recommendBookList = (RecommendBookList) item;
                    RecyclerView rvRecommendBookList = holder.getView(R.id.rv_recommend_booklist);
                    rvRecommendBookList.setLayoutManager(new LinearLayoutManager(mContext));
                    rvRecommendBookList.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
                    rvRecommendBookList.setAdapter(new RecommendBookListAdapter(mContext, recommendBookList.getBooklists(), R.layout.item_recommend_booklist));
                }
                break;
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
            ShowUtils.showToast(tag);
        }
    }

    private class HotReviewAdapter extends BaseRvAdapter<HotReview.ReviewsBean> {
        public HotReviewAdapter(Context context, List<HotReview.ReviewsBean> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, HotReview.ReviewsBean item, int position, int viewType) {
            CircleImageView ivHead = holder.getView(R.id.iv_head);
            HotReview.ReviewsBean.AuthorBean author = item.getAuthor();
            ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + author.getAvatar(),
                    Constant.MALE.equals(author.getGender()) ? R.mipmap.ic_male : R.mipmap.ic_female, ivHead);

            holder.setText(R.id.tv_nickname, author.getNickname())
                    .setText(R.id.tv_user_level, String.format(UIUtils.getString(R.string
                            .book_detail_user_lv), author.getLv()))
                    .setText(R.id.tv_review_title, item.getTitle())
                    .setText(R.id.tv_review_content, item.getContent())
                    .setText(R.id.tv_thumb_up, String.valueOf(item.getHelpful().getYes()));
            PYRatingBar rbBookRating = holder.getView(R.id.rb_book_rating);
            rbBookRating.setStar(item.getRating());
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

            holder.setText(R.id.tv_title, item.getTitle())
                    .setText(R.id.tv_author, item.getAuthor())
                    .setText(R.id.tv_desc, item.getDesc())
                    .setText(R.id.tv_recommend_count, String.format(UIUtils.getString(R.string
                            .book_detail_recommend_book_list_book_count), item.getBookCount()))
                    .setText(R.id.tv_collect_count, String.format(UIUtils.getString(R.string
                            .book_detail_recommend_book_list_collect_count), item.getCollectorCount()));
        }
    }
}
