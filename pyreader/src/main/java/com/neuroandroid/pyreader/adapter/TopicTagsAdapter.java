package com.neuroandroid.pyreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseRvAdapter;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;
import com.neuroandroid.pyreader.model.response.BookListTags;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.neuroandroid.pyreader.widget.dialog.TopicTagsDialog;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicTagsAdapter extends BaseRvAdapter<BookListTags.DataBean> {
    private TopicTagsDialog mTopicTagsDialog;
    private TopicTagsDialog.OnTagClickListener mOnTagClickListener;
    private String mCurrentTag;

    public void setCurrentTag(String currentTag) {
        mCurrentTag = currentTag;
    }

    public void setOnTagClickListener(TopicTagsDialog dialog, TopicTagsDialog.OnTagClickListener onTagClickListener) {
        this.mTopicTagsDialog = dialog;
        mOnTagClickListener = onTagClickListener;
    }

    public TopicTagsAdapter(Context context, List<BookListTags.DataBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, BookListTags.DataBean item, int position, int viewType) {
        holder.setText(R.id.tv_title, item.getName());
        FlowLayout tagLayout = holder.getView(R.id.tag_layout);
        for (String tag : item.getTags()) {
            tagLayout.addView(getTagView(tagLayout, tag));
        }
    }

    private View getTagView(FlowLayout tagLayout, String tag) {
        View tagView = LayoutInflater.from(mContext).inflate(R.layout.item_fab, tagLayout, false);
        NoPaddingTextView tvTag = (NoPaddingTextView) tagView.findViewById(R.id.tv_fab);
        tvTag.setText(tag);
        tagView.setOnClickListener(new TagClickListener(tag));
        if (!UIUtils.isEmpty(mCurrentTag) && mCurrentTag.equals(tag)) {
            tvTag.setTextColor(UIUtils.getColor(R.color.colorPrimary));
        } else {
            tvTag.setTextColor(UIUtils.getColor(R.color.colorGray333));
        }
        return tagView;
    }

    private class TagClickListener implements View.OnClickListener {
        private final String tag;

        public TagClickListener(String tag) {
            this.tag = tag;
        }

        @Override
        public void onClick(View view) {
            mOnTagClickListener.onTagClick(mTopicTagsDialog, tag);
        }
    }
}
