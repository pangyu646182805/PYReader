package com.neuroandroid.pyreader.widget.dialog.base;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.neuroandroid.pyreader.R;

/**
 * Created by NeuroAndroid on 2017/6/16.
 */

public class DialogParams {
    public static final int LAYOUT_PARAM_MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int LAYOUT_PARAM_WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;

    public View contentView = null;
    // 是否弹出软键盘（默认不弹出）
    public boolean isPopSoftKey = false;
    // 点击外部是否可取消(默认点击外部可取消)
    public boolean canceledOnTouchOutside = true;
    // 点击back键是否可以取消dialog
    public boolean cancelable = true;
    // 窗体透明度
    public float dimAmount = 0.6f;
    public int gravity = Gravity.CENTER;
    // 是否需要动画
    public boolean needAnim = true;
    // 对话框的动画资源
    public int dialogAnimResId = R.style.NormalDialogAnim;
    // 对话框宽度
    public int lpWidth = LAYOUT_PARAM_WRAP_CONTENT;
    // 对话框高度
    public int lpHeight = LAYOUT_PARAM_WRAP_CONTENT;

    /**
     * 应用dialog参数配置
     */
    public void applyParams(PYDialog dialog) {
        if (contentView == null) {
            throw new NullPointerException("contentView is null");
        }

        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (isPopSoftKey) {  // 弹出软键盘
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.setCancelable(cancelable);
        window.setGravity(gravity);
        if (needAnim) window.setWindowAnimations(dialogAnimResId);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = lpWidth;
        lp.height = lpHeight;
        lp.alpha = 1.0f;
        lp.dimAmount = dimAmount;
        window.setAttributes(lp);
    }
}
