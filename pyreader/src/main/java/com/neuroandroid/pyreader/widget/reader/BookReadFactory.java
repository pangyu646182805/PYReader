package com.neuroandroid.pyreader.widget.reader;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.BookReadAdapter;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.model.response.ChapterRead;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/7.
 */

public class BookReadFactory {
    private static BookReadFactory sReadFactory;
    /**
     * 章节实体类
     * 一章的内容
     */
    private ChapterRead.Chapter mChapter;
    private List<BookReadBean> mBookReadBeanList;
    private BookReadAdapter mBookReadAdapter;

    public void setChapterContent(BookReadAdapter bookReadAdapter, ChapterRead.Chapter chapter) {
        mChapter = chapter;
        mBookReadAdapter = bookReadAdapter;
        transformChapterContent(chapter);
    }

    private void transformChapterContent(ChapterRead.Chapter chapter) {
        String body = Constant.PARAGRAPH_MARK + chapter.getBody();
        body = body.replaceAll("\n", "\n" + Constant.PARAGRAPH_MARK);
        char[] block = body.toCharArray();
        int blockLength = block.length;

        List<String> lines = new ArrayList<>();
        String line = "";
        float lineWordWidth = 0;
        int currentLineCount = 0;
        mBookReadBeanList = new ArrayList<>();
        BookReadBean bookReadBean;
        for (int i = 0; i < blockLength; i++) {
            String word = charToString(block[i]);
            float wordWidth = mFontPaint.measureText(word);
            lineWordWidth += wordWidth;
            line += word;
            if (lineWordWidth > mVisibleWidth - wordWidth * 0.85f || "\n".equals(word) || i == blockLength - 1) {
                currentLineCount++;
                // 如果累加值超过绘制区域的宽度
                // 则添加当前文本到lines，lineWordWidth清零，重新计算
                lines.add(line);
                lineWordWidth = 0;
                line = "";
                if (currentLineCount >= mLineCount || i == blockLength - 1) {
                    L.e("blockLength - 1 : " + (i == blockLength - 1));
                    // 如果当前累加的行数超过了总行数
                    currentLineCount = 0;
                    bookReadBean = new BookReadBean();
                    bookReadBean.setLines(lines);
                    mBookReadBeanList.add(bookReadBean);
                    lines = new ArrayList<>();
                }
            }
        }
        L.e("page size : " + mBookReadBeanList.size());
        for (BookReadBean readBean : mBookReadBeanList) {
            for (String str : readBean.getLines()) {
                System.out.println(str);
            }
            System.out.println("------------------------------------------------------------");
        }
        mBookReadAdapter.replaceAll(mBookReadBeanList);
    }

    private String charToString(char word) {
        return String.valueOf(word);
    }

    /**
     * 默认阅读界面背景
     */
    private int mReadBackgroungColor = UIUtils.getColor(R.color.defaultReadBackgroundColor);

    private int mFontColor = UIUtils.getColor(R.color.backgroundPanel);

    /**
     * 屏幕宽度和高度
     */
    private int mScreenWidth, mScreenHeight;

    /**
     * 文本字体大小
     */
    private float mFontSize;

    /**
     * 小数格式化
     */
    private DecimalFormat mFormat;

    /**
     * 左右的边距
     */
    private float mLeftAndRightMarginWidth;

    /**
     * 上下边距
     */
    private float mTopAndBottomMarginWidth;

    /**
     * 行间距
     */
    private float mLineSpace;

    /**
     * 段落之间的间距(暂时不考虑)
     */
    private float mParagraphSpace;

    /**
     * 字体
     */
    private Typeface mTypeface;

    /**
     * 绘制区域的宽度和高度
     */
    private float mVisibleWidth, mVisibleHeight;

    /**
     * 每一页显示的行数
     */
    private int mLineCount;

    /**
     * 是否是第一页
     */
    private boolean isFirstPage;

    /**
     * 是否是最后一页
     */
    private boolean isLastPage;

    /**
     * 绘制文本的画笔
     */
    private Paint mFontPaint;

    public static synchronized BookReadFactory getInstance() {
        return sReadFactory;
    }

    public static synchronized BookReadFactory createBookReadFactory(Context context) {
        if (sReadFactory == null) {
            sReadFactory = new BookReadFactory(context);
        }
        return sReadFactory;
    }

    private BookReadFactory(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

        mFormat = new DecimalFormat("#0.0");
        mLeftAndRightMarginWidth = UIUtils.getDimen(R.dimen.x30);
        mTopAndBottomMarginWidth = UIUtils.getDimen(R.dimen.x60);
        mLineSpace = UIUtils.getDimen(R.dimen.y24);
        mParagraphSpace = UIUtils.getDimen(R.dimen.y32);
        mVisibleWidth = mScreenWidth - mLeftAndRightMarginWidth * 2;
        mVisibleHeight = mScreenHeight - mTopAndBottomMarginWidth * 2;
        mFontSize = UIUtils.getRawSize(context, TypedValue.COMPLEX_UNIT_SP, 18);
        mLineCount = (int) (mVisibleHeight / (mFontSize + mLineSpace));

        mFontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFontPaint.setTextAlign(Paint.Align.LEFT);
        mFontPaint.setTextSize(mFontSize);
        mFontPaint.setColor(mFontColor);
        mFontPaint.setSubpixelText(true);  // 有助于文本在LCD屏幕上的显示效果
    }

    /**
     * Setter and Getter
     */
    public int getReadBackgroungColor() {
        return mReadBackgroungColor;
    }

    public int getFontColor() {
        return mFontColor;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public float getFontSize() {
        return mFontSize;
    }

    public DecimalFormat getFormat() {
        return mFormat;
    }

    public float getLeftAndRightMarginWidth() {
        return mLeftAndRightMarginWidth;
    }

    public float getTopAndBottomMarginWidth() {
        return mTopAndBottomMarginWidth;
    }

    public float getLineSpace() {
        return mLineSpace;
    }

    public float getParagraphSpace() {
        return mParagraphSpace;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public float getVisibleWidth() {
        return mVisibleWidth;
    }

    public float getVisibleHeight() {
        return mVisibleHeight;
    }

    public int getLineCount() {
        return mLineCount;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public Paint getFontPaint() {
        return mFontPaint;
    }
}
