package com.neuroandroid.pyreader.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.TopicTagsAdapter;
import com.neuroandroid.pyreader.model.response.BookListTags;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;

/**
 * Created by NeuroAndroid on 2017/7/5.
 */

public class TopicTagsDialog extends PYDialog<TopicTagsDialog> {
    private RecyclerView mRvTags;
    private TopicTagsAdapter mTopicTagsAdapter;
    private BookListTags mBookListTags;

    public TopicTagsDialog setBookListTags(BookListTags bookListTags, String currentTag) {
        mBookListTags = bookListTags;
        if (mTopicTagsAdapter != null) {
            mTopicTagsAdapter.setCurrentTag(currentTag);
            mTopicTagsAdapter.replaceAll(mBookListTags.getData());
        }
        return this;
    }

    public TopicTagsDialog setOnTagClickListener(OnTagClickListener onTagClickListener) {
        if (mTopicTagsAdapter != null) mTopicTagsAdapter.setOnTagClickListener(this, onTagClickListener);
        return this;
    }

    public TopicTagsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_topic_tags_dialog;
    }

    @Override
    protected void initView() {
        mRvTags = getView(R.id.rv_tags);
        mRvTags.setLayoutManager(new LinearLayoutManager(mContext));
        mTopicTagsAdapter = new TopicTagsAdapter(mContext, null, R.layout.item_topic_tags);
        mRvTags.setAdapter(mTopicTagsAdapter);

        setDimAmount(0.2f);
    }

    public interface OnTagClickListener {
        void onTagClick(TopicTagsDialog dialog, String tag);
    }
}
