package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.ImageView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.adapter.base.IMultiItemViewType;
import com.neuroandroid.pyreader.adapter.base.SelectAdapter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.Recommend;
import com.neuroandroid.pyreader.utils.FormatUtils;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/20.
 */

public class RecommendAdapter extends SelectAdapter<Recommend.BooksBean> {
    private static final int VIEW_TYPE_FOOTER = 100;

    public RecommendAdapter(Context context, List<Recommend.BooksBean> dataList, IMultiItemViewType<Recommend.BooksBean> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, Recommend.BooksBean item, int position, int viewType) {
        if (viewType != VIEW_TYPE_FOOTER) {
            AppCompatCheckBox cbRecommend = holder.getView(R.id.cb_recommend);
            if (isSelectMode()) {
                cbRecommend.setVisibility(View.VISIBLE);
            } else {
                cbRecommend.setVisibility(View.GONE);
            }
            ImageView ivBookCover = holder.getView(R.id.iv_book_cover);
            ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + item.getCover(), R.mipmap.cover_default, ivBookCover);

            String subTitle = FormatUtils.getDescriptionTimeFromDateString(item.getUpdated());
            if (!UIUtils.isEmpty(subTitle)) {
                subTitle += ":";
            }
            subTitle += item.getLastChapter();
            holder.setText(R.id.tv_book_title, item.getTitle())
                    .setText(R.id.tv_book_sub_title, subTitle);
        }
    }

    @Override
    protected IMultiItemViewType<Recommend.BooksBean> provideMultiItemViewType() {
        return new IMultiItemViewType<Recommend.BooksBean>() {
            @Override
            public int getItemViewType(int position, Recommend.BooksBean booksBean) {
                return position == getItemCount() - 1 ? VIEW_TYPE_FOOTER : 0;
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == VIEW_TYPE_FOOTER) {
                    return R.layout.item_recomment_footer;
                } else {
                    return R.layout.item_recomment;
                }
            }
        };
    }
}
