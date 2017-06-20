package com.neuroandroid.pyreader.net;

import android.content.Context;

import com.neuroandroid.pyreader.utils.L;
import com.neuroandroid.pyreader.utils.NetworkUtils;
import com.neuroandroid.pyreader.utils.UIUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/8/30.
 */
public class RetrofitUtils {
    public static volatile Retrofit sRetrofit;
    private static final int TIMEOUT_CONNECT = 30; // 10秒
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; // 7天

    private RetrofitUtils() {
    }

    /**
     * 确保该方法在Application类中调用一次
     * needCache 是否需要缓存
     * needForceCache 是否强制从缓存读取数据
     * needLog 是否需要请求网络打印日志
     *
     * @return
     */
    public static Retrofit getInstance(String url) {
        sRetrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getCacheClient(UIUtils.getContext()))
                .build();
        return sRetrofit;
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true).connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);

        // builder.readTimeout(20000, TimeUnit.MILLISECONDS);
        if (L.getGlobalToggle())
            builder.addInterceptor(new LogInterceptor());
        return builder.build();
    }

    private static OkHttpClient getCacheClient(Context ctx) {
        File httpCacheDirectory = new File(ctx.getCacheDir(), ctx.getPackageName());
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache)
                // 连接失败是否重新请求
                .retryOnConnectionFailure(true).connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                // 没网络时的拦截器
                .addInterceptor(getInterceptor(ctx))
                // 有网络时的拦截器
                .addNetworkInterceptor(getNetWorkInterceptor(ctx));
        if (L.getGlobalToggle())
            builder.addInterceptor(new LogInterceptor());
        return builder.build();
    }

    private static Interceptor getInterceptor(final Context ctx) {
        return chain -> {
            Request request = chain.request();
            if (!NetworkUtils.isConnected(ctx)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            return chain.proceed(request);
        };
    }

    private static Interceptor getNetWorkInterceptor(final Context ctx) {
        return chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnected(ctx)) {
                // int maxAge = 0 * 60;
                // 有网络时 设置缓存超时时间0个小时
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + TIMEOUT_CONNECT)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时，设置超时为1周
                // int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
            /*return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + 5)
                    .build();*/
        };
    }
}
