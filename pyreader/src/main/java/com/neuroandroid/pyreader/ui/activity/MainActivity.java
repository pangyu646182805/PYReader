package com.neuroandroid.pyreader.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialcab.MaterialCab;
import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.PYReaderPagerAdapter;
import com.neuroandroid.pyreader.base.BaseActivity;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.event.ChooseSexEvent;
import com.neuroandroid.pyreader.interfaces.MaterialCabCallBack;
import com.neuroandroid.pyreader.manager.SettingManager;
import com.neuroandroid.pyreader.ui.fragment.CategoryFragment;
import com.neuroandroid.pyreader.ui.fragment.RankingFragment;
import com.neuroandroid.pyreader.ui.fragment.SearchFragment;
import com.neuroandroid.pyreader.ui.fragment.TopicBookListFragment;
import com.neuroandroid.pyreader.utils.FragmentUtils;
import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.ThemeUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.dialog.ChooseSexDialog;
import com.neuroandroid.pyreader.widget.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.polaric.colorful.ColorPickerDialog;
import org.polaric.colorful.Colorful;

import butterknife.BindView;

/**
 *
 */
public class MainActivity extends BaseActivity implements MaterialCabCallBack {
    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    private MainActivityFragmentCallbacks mCurrentFragment;
    private MaterialCab mCab;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        // 不允许用户截屏当前页面
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        PYReaderPagerAdapter pyReaderPagerAdapter = new PYReaderPagerAdapter(getSupportFragmentManager(), this);
        mVpContent.setAdapter(pyReaderPagerAdapter);
        mVpContent.setOffscreenPageLimit(pyReaderPagerAdapter.getCount() - 1);
        mTabs.setViewPager(mVpContent);

        UIUtils.getHandler().postDelayed(() -> showChooseSexDialog(), 500);
        // Util.resolveColor(this.mContext, R.attr.colorPrimary, 0)

        if (ThemeUtils.isDarkMode())
            mAppBarLayout.setBackgroundColor(UIUtils.getColor(R.color.backgroundColorDark));
    }

    /**
     * 跳转到微信扫一扫界面
     */
    public static void toWeChatScanDirect(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(335544320);
            intent.setAction("android.intent.action.VIEW");
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    /**
     * 用户选择性别dialog
     */
    private void showChooseSexDialog() {
        if (!SettingManager.isUserChooseSex(this)) {
            new ChooseSexDialog(this)
                    .setOnLeftBtnClickListener((dialog, view) -> {
                        SettingManager.saveChooseSex(this, Constant.MALE);
                        EventBus.getDefault().post(new ChooseSexEvent());
                        dialog.dismissDialog();
                    })
                    .setOnRightBtnClickListener((dialog, view) -> {
                        SettingManager.saveChooseSex(this, Constant.FEMALE);
                        EventBus.getDefault().post(new ChooseSexEvent());
                        dialog.dismissDialog();
                    }).showDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_dark_theme);
        item.setTitle(ThemeUtils.isDarkMode() ? Constant.LIGHT_THEME : Constant.DARK_THEME);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearchFragment();
                break;
            case R.id.action_scan_book:
                ShowUtils.showToast("扫描书籍");
                break;
            case R.id.action_theme:
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this);
                colorPickerDialog.setOnColorSelectedListener(themeColor -> {
                    Colorful.config(this)
                            .primaryColor(themeColor)
                            .accentColor(themeColor)
                            .translucent(false)
                            .dark(ThemeUtils.isDarkMode())
                            .apply();
                    changeTheme();
                });
                colorPickerDialog.show();
                break;
            case R.id.action_dark_theme:
                boolean darkMode;
                if (Constant.LIGHT_THEME.equals(item.getTitle())) {
                    darkMode = false;
                } else {
                    darkMode = true;
                }
                Colorful.config(this)
                        .dark(darkMode)
                        .apply();
                changeTheme();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!handleBackPress())
            super.onBackPressed();
    }

    /**
     * 返回true则会屏蔽返回事件
     */
    public boolean handleBackPress() {
        if (mCurrentFragment != null && mCurrentFragment.handleBackPress()) {
            return true;
        } else {
            if (mCurrentFragment != null) {
                FragmentUtils.removeFragment((Fragment) mCurrentFragment);
                mCurrentFragment = null;
                return true;
            }
        }
        return closeMaterialCab();
    }

    /**
     * 关闭MaterialCab
     */
    private boolean closeMaterialCab() {
        if (mCab != null && mCab.isActive()) {
            mCab.finish();
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public MaterialCab openCab(int menuRes, MaterialCab.Callback callback) {
        if (mCab != null && mCab.isActive()) mCab.finish();
        mCab = new MaterialCab(this, R.id.cab_stub)
                .setMenu(menuRes)
                .setPopupMenuTheme(R.style.ThemeOverlay_AppCompat_Light)
                .setCloseDrawableRes(R.drawable.ic_close_white)
                .setBackgroundColorAttr(R.attr.colorPrimary)
                .start(new MaterialCab.Callback() {
                    @Override
                    public boolean onCabCreated(MaterialCab cab, Menu menu) {
                        L.e("onCabCreated");
                        return callback.onCabCreated(cab, menu);
                    }

                    @Override
                    public boolean onCabItemClicked(MenuItem item) {
                        L.e("onCabItemClicked");
                        return callback.onCabItemClicked(item);
                    }

                    @Override
                    public boolean onCabFinished(MaterialCab cab) {
                        L.e("onCabFinished");
                        return callback.onCabFinished(cab);
                    }
                });
        if (ThemeUtils.isDarkMode())
            mCab.setBackgroundColor(UIUtils.getColor(R.color.backgroundColorDark));
        return mCab;
    }

    /**
     * 打开SearchFragment
     */
    private void openSearchFragment() {
        setCurrentFragment(new SearchFragment());
    }

    /**
     * 打开CategoryFragment
     */
    public void openCategoryFragment() {
        setCurrentFragment(new CategoryFragment());
    }

    /**
     * 打开RankingFragment
     */
    public void openRankingFragment() {
        setCurrentFragment(new RankingFragment());
    }

    /**
     * 打开TopicFragment
     */
    public void openTopicBookListFragment() {
        setCurrentFragment(new TopicBookListFragment());
    }

    private void setCurrentFragment(Fragment fragment) {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.fl_container, false);
        mCurrentFragment = (MainActivityFragmentCallbacks) fragment;
    }

    /**
     * 处理fragment返回事件
     */
    public interface MainActivityFragmentCallbacks {
        boolean handleBackPress();
    }
}
