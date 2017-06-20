package com.neuroandroid.pyreader.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.neuroandroid.pyreader.R;
import com.neuroandroid.pyreader.adapter.PYReaderPagerAdapter;
import com.neuroandroid.pyreader.base.BaseActivity;
import com.neuroandroid.pyreader.config.Constant;
import com.neuroandroid.pyreader.event.ChooseSexEvent;
import com.neuroandroid.pyreader.manager.SettingManager;
import com.neuroandroid.pyreader.utils.ShowUtils;
import com.neuroandroid.pyreader.utils.UIUtils;
import com.neuroandroid.pyreader.widget.dialog.ChooseSexDialog;
import com.neuroandroid.pyreader.widget.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        PYReaderPagerAdapter pyReaderPagerAdapter = new PYReaderPagerAdapter(getSupportFragmentManager(), this);
        mVpContent.setAdapter(pyReaderPagerAdapter);
        mTabs.setViewPager(mVpContent);

        UIUtils.getHandler().postDelayed(() -> showChooseSexDialog(), 500);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ShowUtils.showToast("搜索");
                break;
            case R.id.action_scan_book:
                ShowUtils.showToast("扫描书籍");
                break;
            case R.id.action_settings:
                ShowUtils.showToast("设置");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
