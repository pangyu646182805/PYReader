package com.neuroandroid.pyreader.widget.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.base.BaseViewHolder;

/**
 * Created by NeuroAndroid on 2017/6/15.
 */

public abstract class PYDialog<T extends PYDialog<T>> extends Dialog {
    protected final Context mContext;
    private final DialogParams mDialogParams;
    private DialogViewHelper mViewHelper;

    public DialogParams getDialogParams() {
        return mDialogParams;
    }

    public DialogViewHelper getViewHelper() {
        return mViewHelper;
    }

    public PYDialog(@NonNull Context context) {
        this(context, R.style.NormalDialogStyle);
    }

    public PYDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        mDialogParams = new DialogParams();
        initContentView();
        initView();
    }

    /**
     * 设置dialog布局id
     * 需要先设置布局id否则会抛出异常
     */
    private void initContentView() {
        View contentView = LayoutInflater.from(mContext).inflate(getLayoutResId(), null);
        mDialogParams.contentView = contentView;
        mViewHelper = new DialogViewHelper(contentView);
    }

    private T createDialog() {
        mDialogParams.applyParams(this);
        return (T) this;
    }

    /**
     * 显示dialog
     */
    public T showDialog() {
        createDialog().show();
        return (T) this;
    }

    /**
     * 隐藏dialog
     */
    public T dismissDialog() {
        if (isShowing()) {
            try {
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) this;
    }

    /**
     * dialog填充屏幕宽度
     */
    public T setFullWidth() {
        mDialogParams.lpWidth = DialogParams.LAYOUT_PARAM_MATCH_PARENT;
        return (T) this;
    }

    /**
     * dialog填充屏幕高度
     */
    public T setFullHeight() {
        mDialogParams.lpHeight = DialogParams.LAYOUT_PARAM_MATCH_PARENT;
        return (T) this;
    }

    /**
     * 设置是否弹出dialog的时候也弹出键盘
     */
    public T setPopSoftKey(boolean isPopSoftKey) {
        mDialogParams.isPopSoftKey = isPopSoftKey;
        return (T) this;
    }

    /**
     * 设置dialog点击外部是否可取消
     */
    public T setDialogCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mDialogParams.canceledOnTouchOutside = canceledOnTouchOutside;
        return (T) this;
    }

    /**
     * 设置点击back键是否可以取消dialog
     */
    public T setDialogCancelable(boolean cancelable) {
        mDialogParams.cancelable = cancelable;
        return (T) this;
    }

    /**
     * 设置dialog窗体透明度
     */
    public T setDimAmount(float dimAmount) {
        mDialogParams.dimAmount = dimAmount;
        return (T) this;
    }

    /**
     * 设置dialog显示位置
     */
    public T setGravity(int gravity) {
        mDialogParams.gravity = gravity;
        return (T) this;
    }

    /**
     * 设置dialog显示是否需要动画
     */
    public T needAnim(boolean needAnim) {
        mDialogParams.needAnim = needAnim;
        return (T) this;
    }

    /**
     * 设置dialog动画资源id
     */
    public T setAnimResId(int dialogAnimResId) {
        mDialogParams.dialogAnimResId = dialogAnimResId;
        return (T) this;
    }

    /**
     * 设置dialog窗体宽度和高度
     */
    public T setWidthAndHeight(int width, int height) {
        mDialogParams.lpWidth = width;
        mDialogParams.lpHeight = height;
        return (T) this;
    }

    /**
     * 设置dialog从下方弹出
     */
    public T setFromBottom() {
        mDialogParams.gravity = Gravity.BOTTOM;
        return setAnimResId(R.style.FromBottomDialogAnim);
    }

    public T setText(@IdRes int viewId, String text) {
        mViewHelper.setText(viewId, text);
        return (T) this;
    }

    public T setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            mViewHelper.setOnClickListener(viewId, view -> dismiss());
        } else {
            mViewHelper.setOnClickListener(viewId, onClickListener);
        }
        return (T) this;
    }

    public T setVisibility(@IdRes int viewId, int visibility) {
        mViewHelper.setVisibility(viewId, visibility);
        return (T) this;
    }

    /****************通用的配置********************/
    public T setDialogTitle(String title) {
        return setText(R.id.tv_title, title);
    }

    /**
     * 设置左边按钮的文本
     */
    public T setLeftButtonText(String leftButtonText) {
        return setText(R.id.tv_left, leftButtonText);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置右边按钮的文本
     */
    public T setRightButtonText(String rightButtonText) {
        return setText(R.id.tv_right, rightButtonText);
    }

    public T setOnLeftBtnClickListener(OnDialogClickListener<T> onLeftBtnClickListener) {
        if (onLeftBtnClickListener == null) {
            getView(R.id.tv_left).setOnClickListener(view -> dismissDialog());
        } else {
            getView(R.id.tv_left).setOnClickListener(view -> {
                if (onLeftBtnClickListener != null) onLeftBtnClickListener.onClick((T) this, view);
            });
        }
        return (T) this;
    }

    public T setOnRightBtnClickListener(OnDialogClickListener<T> onRightBtnClickListener) {
        if (onRightBtnClickListener == null) {
            getView(R.id.tv_right).setOnClickListener(view -> dismissDialog());
        } else {
            getView(R.id.tv_right).setOnClickListener(view -> {
                if (onRightBtnClickListener != null) onRightBtnClickListener.onClick((T) this, view);
            });
        }
        return (T) this;
    }

    /**
     * 显示dialog标题栏
     */
    public T showTitle() {
        return setVisibility(R.id.tv_title, View.VISIBLE);
    }

    /**
     * 显示dialog底部栏
     */
    public T showButton() {
        return setVisibility(R.id.ll_bottom, View.VISIBLE);
    }

    /**
     * 隐藏dialog标题栏
     */
    public T setNoTitle() {
        return setVisibility(R.id.tv_title, View.GONE);
    }

    /**
     * 隐藏dialog底部栏
     */
    public T setNoButton() {
        return setVisibility(R.id.ll_bottom, View.GONE);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected void initView() {}

    public interface OnDialogClickListener<T> {
        void onClick(T dialog, View view);
    }


    public interface OnDialogItemClickListener<T, ITEM> {
        void onDialogItemClick(T dialog, BaseViewHolder holder, int position, ITEM item);
    }
}
