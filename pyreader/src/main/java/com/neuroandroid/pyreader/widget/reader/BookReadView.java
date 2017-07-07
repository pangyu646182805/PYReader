package com.neuroandroid.pyreader.widget.reader;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.utils.L;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/7/7.
 */

public class BookReadView extends View {
    private final Context mContext;

    private BookReadBean mBookReadBean;

    public void setBookReadBean(BookReadBean bookReadBean) {
        mBookReadBean = bookReadBean;
        invalidate();
    }

    private BookReadFactory mBookReadFactory;
    private GestureDetector mGestureDetector;

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
        mGestureDetector = new GestureDetector(mContext, new BookReadDetector());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBookReadFactory.getReadBackgroungColor());
        if (mBookReadBean != null) {
            List<String> lines = mBookReadBean.getLines();
            int lineCount = lines.size();
            float lineTextY = mBookReadFactory.getTopAndBottomMarginWidth() + mBookReadFactory.getFontSize();
            for (int i = 0; i < lineCount; i++) {
                String line = lines.get(i);
                if (line.contains(Constant.PARAGRAPH_MARK)) line = line.replace(Constant.PARAGRAPH_MARK, "\u3000\u3000");
                canvas.drawText(line, mBookReadFactory.getLeftAndRightMarginWidth(),
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private class BookReadDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            L.e(e.getX() + " : " + e.getY());
            return super.onSingleTapConfirmed(e);
        }
    }
}
