package com.lgw.base;

import android.support.multidex.MultiDexApplication;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.lgw.BuildConfig;
import com.lgw.Utils.CrashHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


public class BaseApplication extends MultiDexApplication {

    public static final String a = "测试是";
    public static HashMap<String, String> AuthorityList = new HashMap<>();
    public static boolean isLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        //----------------------------------工具集合----------------------------------//
        Utils.init(this);
        //----------------------------------日志控制----------------------------------//
        LogUtils.getConfig().setLogSwitch(BuildConfig.logSwitch).setLogHeadSwitch(false)
                .setBorderSwitch(false).setGlobalTag("日志");
        //----------------------------------网络请求----------------------------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("os", "android");
        HttpParams params = new HttpParams();
        params.put("os", "Android");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor =new  HttpLoggingInterceptor("日志");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(0)
                .addCommonHeaders(headers)
                .addCommonParams(params);
        //----------------------------------ARouter----------------------------------//
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        //----------------------------------LeakCanary----------------------------------//
        if (LeakCanary.isInAnalyzerProcess(getApplicationContext())) {
            return;
        }
        LeakCanary.install(this);

        CrashHandler.getInstance().init(getApplicationContext());
    }
}
