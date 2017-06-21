package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.base.BaseResponse;
import com.neuroandroid.pyreader.model.response.BookDetail;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.model.response.RecommendBookList;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/21.
 */

public class BookDetailAdapter extends BaseRvAdapter<BaseResponse> {
    public static final int VIEW_TYPE_BOOK_DETAIL_HEADER = 0;
    public static final int VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW = 1;
    public static final int VIEW_TYPE_BOOK_DETAIL_COMMUNITY = 2;
    public static final int VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST = 3;

    private int mToolBarHeight;

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
                    holder.setText(R.id.tv_book_title, bookDetail.getTitle())
                            .setText(R.id.tv_book_author, bookDetail.getAuthor());
                }
                break;
            case VIEW_TYPE_BOOK_DETAIL_HOT_REVIEW:
                HotReview hotReview = (HotReview) item;
                break;
            case VIEW_TYPE_BOOK_DETAIL_COMMUNITY:
                break;
            case VIEW_TYPE_BOOK_DETAIL_RECOMMEND_BOOK_LIST:
                RecommendBookList recommendBookList = (RecommendBookList) item;
                break;
        }
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
}
