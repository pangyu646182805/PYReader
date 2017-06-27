package com.neuroandroid.pyreader.adapter;

import android.content.Context;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.DiscussionList;
import com.neuroandroid.pyreader.utils.FormatUtils;
import com.neuroandroid.pyreader.utils.ImageLoader;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NeuroAndroid on 2017/6/27.
 */

public class BookDiscussionAdapter extends BaseRvAdapter<DiscussionList.PostsBean> {
    public BookDiscussionAdapter(Context context, List<DiscussionList.PostsBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, DiscussionList.PostsBean item, int position, int viewType) {
        CircleImageView ivHead = holder.getView(R.id.iv_head);
        DiscussionList.PostsBean.AuthorBean author = item.getAuthor();
        ImageLoader.getInstance().displayImage(mContext, Constant.IMG_BASE_URL + author.getAvatar(),
                Constant.MALE.equals(author.getGender()) ? R.mipmap.ic_male : R.mipmap.ic_female, ivHead);

        holder.setText(R.id.tv_nickname, author.getNickname())
                .setText(R.id.tv_user_level, String.format(UIUtils.getString(R.string
                        .book_detail_user_lv), author.getLv()))
                .setText(R.id.tv_discussion_time, FormatUtils.getDescriptionTimeFromDateString(item.getCreated()))
                .setText(R.id.tv_discussion_title, item.getTitle())
                .setText(R.id.tv_comment_count, String.valueOf(item.getCommentCount()))
                .setText(R.id.tv_like_count, String.valueOf(item.getLikeCount()));
    }
}
