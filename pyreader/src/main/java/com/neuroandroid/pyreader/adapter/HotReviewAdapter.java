package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.HotReview;
import com.neuroandroid.pyreader.utils.FormatUtils;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.PYRatingBar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class HotReviewAdapter extends BaseRvAdapter<HotReview.ReviewsBean> {
    private boolean showReviewTime;

    public void setShowReviewTime(boolean showReviewTime) {
        this.showReviewTime = showReviewTime;
    }

    public HotReviewAdapter(Context context, List<HotReview.ReviewsBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, HotReview.ReviewsBean item, int position, int viewType) {
        int mainColor = ThemeUtils.getMainColor();
        int subColor = ThemeUtils.getSubColor();

        CircleImageView ivHead = holder.getView(R.id.iv_head);
        HotReview.ReviewsBean.AuthorBean author = item.getAuthor();
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + author.getAvatar(),
                Constant.MALE.equals(author.getGender()) ? R.mipmap.ic_male : R.mipmap.ic_female, ivHead);

        holder.setVisibility(R.id.tv_review_time, showReviewTime ? View.VISIBLE : View.GONE)
                .setText(R.id.tv_nickname, author.getNickname())
                .setText(R.id.tv_user_level, String.format(UIUtils.getString(R.string
                        .book_detail_user_lv), author.getLv()))
                .setText(R.id.tv_review_title, item.getTitle())
                .setTextColor(R.id.tv_review_title, mainColor)
                .setText(R.id.tv_review_content, item.getContent())
                .setTextColor(R.id.tv_review_content, subColor)
                .setText(R.id.tv_thumb_up, String.valueOf(item.getHelpful().getYes()))
                .setTextColor(R.id.tv_thumb_up, subColor);
        PYRatingBar rbBookRating = holder.getView(R.id.rb_book_rating);
        rbBookRating.setStar(item.getRating());
        if (showReviewTime) holder.setText(R.id.tv_review_time, FormatUtils.getDescriptionTimeFromDateString(item.getCreated()));
    }
}
