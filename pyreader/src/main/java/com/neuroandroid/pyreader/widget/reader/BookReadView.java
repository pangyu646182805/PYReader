package com.neuroandroid.pyreader.widget.reader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/7.
 */

public class BookReadView extends View {
    private final Context mContext;
    private static final String TWO_SPACE = "空格";

    private BookReadBean mBookReadBean;
    private float mTwoSpaceWidth;

    /**
     * 中间点击菜单区域
     */
    private RectF mCenterRect = new RectF();

    /**
     * 测量文本
     */
    private Rect mFontRect = new Rect();

    private OnReadStateChangeListener mReadStateChangeListener;

    private String mCurrentTime = null;

    /**
     * 当前页数和总页数
     */
    private int mCurrentPage, mTotalPage;

    /**
     * 当前电池电量
     */
    private int mCurrentBatteryLevel;

    public void setReadStateChangeListener(OnReadStateChangeListener readStateChangeListener) {
        mReadStateChangeListener = readStateChangeListener;
    }

    public void setBookReadBean(BookReadBean bookReadBean, String updateTime, int batteryLevel) {
        mBookReadBean = bookReadBean;
        this.mCurrentTime = updateTime;
        this.mCurrentBatteryLevel = batteryLevel;
        invalidate();
    }

    private BookReadFactory mBookReadFactory;

    public BookReadView(Context context) {
        this(context, null);
    }

    public BookReadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mBookReadFactory = BookReadFactory.getInstance();
        mTwoSpaceWidth = mBookReadFactory.getFontPaint().measureText(TWO_SPACE);

        float left = UIUtils.getDimen(R.dimen.x200);
        float top = UIUtils.getDimen(R.dimen.y360);
        mCenterRect.set(left, top,
                mBookReadFactory.getScreenWidth() - left, mBookReadFactory.getScreenHeight() - top);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBookReadFactory.getReadBackgroundColor());
        float dimen16 = UIUtils.getDimen(R.dimen.x16);
        float horizontalY = 0;
        float verticalX;
        // 绘制时间
        if (!UIUtils.isEmpty(mCurrentTime)) {
            UIUtils.getTextBounds(mBookReadFactory.getOtherFontPaint(), mCurrentTime, mFontRect);
            horizontalY = mBookReadFactory.getScreenHeight() - (mBookReadFactory.getTopAndBottomMarginWidth() - mFontRect.height()) / 2;
            canvas.drawText(mCurrentTime, (mBookReadFactory.getScreenWidth() - mFontRect.width()) / 2,
                    horizontalY, mBookReadFactory.getOtherFontPaint());
        }

        // 绘制电池图标和电池电量图标
        Path batteryIconPath = mBookReadFactory.getBatteryIconPath();
        drawBatteryIconAndProgressBackground(canvas, batteryIconPath);

        if (mBookReadBean != null) {
            // 绘制当前页数和总页数
            mCurrentPage = mBookReadBean.getCurrentPage();
            mTotalPage = mBookReadBean.getTotalPage();
            String pageStr = mCurrentPage + "/" + mTotalPage;
            UIUtils.getTextBounds(mBookReadFactory.getOtherFontPaint(), pageStr, mFontRect);
            verticalX = mBookReadFactory.getScreenWidth() - mBookReadFactory.getLeftAndRightMarginWidth()
                    - mFontRect.width() - dimen16;
            canvas.drawText(pageStr, verticalX, horizontalY,
                    mBookReadFactory.getOtherFontPaint());

            horizontalY = (mBookReadFactory.getTopAndBottomMarginWidth() + mFontRect.height()) / 2;
            // 画书籍名称
            String bookTitle = mBookReadBean.getTitle();
            UIUtils.getTextBounds(mBookReadFactory.getOtherFontPaint(), bookTitle, mFontRect);
            canvas.drawText(bookTitle, mBookReadFactory.getLeftAndRightMarginWidth(),
                    horizontalY, mBookReadFactory.getOtherFontPaint());

            // 画章节标题
            String chapterTitle = mBookReadBean.getChapterTitle();
            UIUtils.getTextBounds(mBookReadFactory.getOtherFontPaint(), chapterTitle, mFontRect);
            verticalX = mBookReadFactory.getScreenWidth() - mBookReadFactory.getLeftAndRightMarginWidth()
                    - mFontRect.width() - dimen16;
            canvas.drawText(chapterTitle, verticalX,
                    horizontalY, mBookReadFactory.getOtherFontPaint());

            // 绘制正文
            List<String> lines = mBookReadBean.getLines();
            int lineCount = lines.size();
            float lineTextY = mBookReadFactory.getTopAndBottomMarginWidth() + mBookReadFactory.getFontSize();
            float lineTextX;
            for (int i = 0; i < lineCount; i++) {
                String line = lines.get(i);
                if (line.contains(Constant.PARAGRAPH_MARK)) {
                    line = line.replace(Constant.PARAGRAPH_MARK, "");
                    lineTextX = mBookReadFactory.getLeftAndRightMarginWidth() + mTwoSpaceWidth;
                } else {
                    lineTextX = mBookReadFactory.getLeftAndRightMarginWidth();
                }
                canvas.drawText(line, lineTextX,
                        lineTextY, mBookReadFactory.getFontPaint());
                lineTextY += mBookReadFactory.getFontSize() + mBookReadFactory.getLineSpace();
            }
        }
        /*Paint paint = new Paint();
        paint.setColor(Color.parseColor("#33ff0000"));
        canvas.drawRect(mBookReadFactory.getLeftAndRightMarginWidth(),
                mBookReadFactory.getTopAndBottomMarginWidth(),
                mBookReadFactory.getScreenWidth() - mBookReadFactory.getLeftAndRightMarginWidth(),
                mBookReadFactory.getScreenHeight() - mBookReadFactory.getTopAndBottomMarginWidth(), paint);*/
    }

    /**
     * 绘制电池图标背景
     */
    private void drawBatteryIconAndProgressBackground(Canvas canvas, Path batteryIconPath) {
        batteryIconPath.reset();
        mBookReadFactory.getBatteryIconPaint().setStyle(Paint.Style.STROKE);
        float batteryIconHeight = mBookReadFactory.getBatteryIconWidth() / 2;
        float batteryProminentHeight = batteryIconHeight / 3;
        float batteryProminentWidth = mBookReadFactory.getBatteryIconWidth() / 7;

        float startX = mBookReadFactory.getLeftAndRightMarginWidth() + batteryProminentWidth;
        float startY = mBookReadFactory.getScreenHeight() - (mBookReadFactory.getTopAndBottomMarginWidth() + batteryIconHeight) / 2;
        batteryIconPath.moveTo(startX, startY);
        batteryIconPath.lineTo(startX, startY + batteryProminentHeight);
        batteryIconPath.lineTo(startX - batteryProminentWidth, startY + batteryProminentHeight);
        batteryIconPath.lineTo(startX - batteryProminentWidth, startY + 2 * batteryProminentHeight);
        batteryIconPath.lineTo(startX, startY + 2 * batteryProminentHeight);
        batteryIconPath.lineTo(startX, startY + batteryIconHeight);
        batteryIconPath.lineTo(startX + mBookReadFactory.getBatteryIconWidth(), startY + batteryIconHeight);
        batteryIconPath.lineTo(startX + mBookReadFactory.getBatteryIconWidth(), startY);
        batteryIconPath.close();
        canvas.drawPath(batteryIconPath, mBookReadFactory.getBatteryIconPaint());

        batteryIconPath.reset();
        mBookReadFactory.getBatteryIconPaint().setStyle(Paint.Style.FILL);

        startX = mBookReadFactory.getLeftAndRightMarginWidth() + batteryProminentWidth + mBookReadFactory.getBatteryIconWidth();
        // 进度百分比
        float percent = mCurrentBatteryLevel * 1.0f / 100;
        // 电池进度的宽度
        float batteryProgressWidth = percent * mBookReadFactory.getBatteryIconWidth();

        batteryIconPath.moveTo(startX, startY);
        batteryIconPath.lineTo(startX, startY + batteryIconHeight);
        batteryIconPath.lineTo(startX - batteryProgressWidth, startY + batteryIconHeight);
        batteryIconPath.lineTo(startX - batteryProgressWidth, startY);
        batteryIconPath.close();
        canvas.drawPath(batteryIconPath, mBookReadFactory.getBatteryIconPaint());

        // 绘制电池电量
        startX += UIUtils.getDimen(R.dimen.x8);
        startY += batteryIconHeight;
        canvas.drawText(String.valueOf(mCurrentBatteryLevel), startX, startY, mBookReadFactory.getOtherFontPaint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float clickX = event.getX();
            float clickY = event.getY();
            if (mCenterRect.contains(clickX, clickY)) {
                if (mReadStateChangeListener != null) {
                    mReadStateChangeListener.onCenterClick();
                }
            } else {
                if (clickX <= mBookReadFactory.getScreenWidth() * 0.5f) {
                    if (mReadStateChangeListener != null) {
                        mReadStateChangeListener.onPrePage();
                    }
                } else {
                    if (mReadStateChangeListener != null) {
                        mReadStateChangeListener.onNextPage();
                    }
                }
            }
        }
        return true;
    }

    public interface OnReadStateChangeListener {
        /**
         * 当章节变化的回调
         *
         * @param oldChapter 原来的章节
         * @param newChapter 新的章节
         */
        void onChapterChanged(int oldChapter, int newChapter);

        /**
         * 在当前章节翻页时page变化的回调
         *
         * @param currentChapter 当前章节
         * @param page           当前page
         */
        void onPageChanged(int currentChapter, int page);

        /**
         * 点击了中心区域
         */
        void onCenterClick();

        /**
         * 下一页
         */
        void onNextPage();

        /**
         * 上一页
         */
        void onPrePage();
    }
}
