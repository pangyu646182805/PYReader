package com.neuroandroid.pyreader.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.widget.reader.BookReadFactory;

import org.polaric.colorful.Colorful;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseApplication extends Application {
    private static Context sContext;
    private static Handler sHandler;

    public static Handler getHandler() {
        return sHandler;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 全局设置是否需要Log打印
         */
        L.setGlobalToggle(true);
        sContext = getApplicationContext();
        sHandler = new Handler();
        BookReadFactory.createBookReadFactory(this);

        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.BLUE)
                .accentColor(Colorful.ThemeColor.BLUE)
                .translucent(true)
                .dark(false);
        Colorful.init(this);
    }
}
