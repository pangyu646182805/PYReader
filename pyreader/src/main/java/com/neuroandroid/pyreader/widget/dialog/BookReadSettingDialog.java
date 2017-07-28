package com.neuroandroid.pyreader.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookReadThemeAdapter;
import com.neuroandroid.pyreader.bean.BookReadThemeBean;
import com.neuroandroid.pyreader.event.BookReadSettingEvent;
import com.neuroandroid.pyreader.utils.BookReadSettingUtils;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.NoPaddingTextView;
import com.neuroandroid.pyreader.widget.dialog.base.DialogViewHelper;
import com.neuroandroid.pyreader.widget.dialog.base.PYDialog;
import com.xw.repo.BubbleSeekBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/27.
 */
public class BookReadSettingDialog extends PYDialog<BookReadSettingDialog> {
    /**
     * 屏幕亮度
     */
    private int mScreenBrightness;

    /**
     * 屏幕亮度跟随系统
     */
    private boolean mFollowSystem = true;
    private NoPaddingTextView mTvFollowSystem;
    private BubbleSeekBar mSbProgress;

    public BookReadSettingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_book_read_setting;
    }

    @Override
    protected void initView() {
        DialogViewHelper viewHelper = getViewHelper();
        RecyclerView rvBookReadTheme = viewHelper.getView(R.id.rv_book_read_theme);
        rvBookReadTheme.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        List<BookReadThemeBean> bookReadThemeBeanList = BookReadSettingUtils.getBookReadThemeBeanList();
        BookReadThemeAdapter bookReadThemeAdapter = new BookReadThemeAdapter(mContext, bookReadThemeBeanList, R.layout.item_book_read_theme);
        rvBookReadTheme.setAdapter(bookReadThemeAdapter);

        bookReadThemeAdapter.setOnItemClickListener((holder, position, item) -> {
            // 保存选择的theme
            EventBus.getDefault().post(new BookReadSettingEvent().setBookReadThemeBean(item));
        });

        mSbProgress = viewHelper.getView(R.id.sb_progress);
        mScreenBrightness = BookReadSettingUtils.getScreenBrightness(mContext);
        if (mScreenBrightness == BookReadSettingUtils.FOLLOW_SYSTEM) {
            mFollowSystem = true;
            mScreenBrightness = UIUtils.getScreenBrightness(mContext);
        } else {
            mFollowSystem = false;
        }
        mSbProgress.setProgress(mScreenBrightness * 1.0f / 255 * 100);
        mSbProgress.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                super.onProgressChanged(progress, progressFloat);
                int brightness = (int) (progressFloat / 100 * 255);
                UIUtils.setScreenBrightness((Activity) mContext, brightness);
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {
                super.getProgressOnActionUp(progress, progressFloat);
                if (mFollowSystem) {
                    selected(mTvFollowSystem, false);
                    BookReadSettingUtils.saveScreenBrightness(mContext, (int) (progressFloat / 100 * 255));
                    mFollowSystem = false;
                }
            }
        });

        mTvFollowSystem = viewHelper.getView(R.id.tv_follow_system);
        selected(mTvFollowSystem, mFollowSystem);
        mTvFollowSystem.setOnClickListener(view -> {
            if (!mFollowSystem) {
                mSbProgress.setProgress(mScreenBrightness * 1.0f / 255 * 100);
                UIUtils.setScreenBrightness((Activity) mContext, mScreenBrightness);
                mFollowSystem = true;
                selected(mTvFollowSystem, mFollowSystem);
                BookReadSettingUtils.saveScreenBrightness(mContext, BookReadSettingUtils.FOLLOW_SYSTEM);
            } else {
                ShowUtils.showToast("无效");
            }
        });

    }

    private void selected(TextView tv, boolean selected) {
        tv.setBackgroundResource(selected ? R.drawable.border_color_primary : R.drawable.shape_white_color_primary_selector);
        tv.setActivated(selected);
    }
}
