package com.lgw.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.lgw.activity.LoginActivity;
import com.lgw.base.BaseApplication;
import com.lgw.bean.MenuItem;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/6/26
 * ================================
 */
public class SessionInterface {
    private Context mContext;
    public static boolean isClickMenu = false;
    public static MenuItem menuParams;

    public SessionInterface(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public Boolean Authentication(String actionId) {
        Boolean isAlow = false;
        if (BaseApplication.AuthorityList.containsKey(actionId)) {
            isAlow = true;
        } else {
            if (LoginState()) {
                isAlow = true;
            } else {
                isAlow = false;
            }
        }
        return isAlow;
    }

    public boolean LoginState() {
        return BaseApplication.isLogin;
    }

    public void isFragOrAty(MenuItem item) {
        if (!BaseApplication.isLogin && !Authentication(item.getActionId())) {
            isClickMenu = true;
            menuParams = item;
            ToastUtils.showShort("请登录");
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            return;
        }
        try {
            Class cl = Class.forName(item.getActionUrl());
            Object o = cl.newInstance();
            if (o instanceof Activity) {
                Intent intent = new Intent(mContext, cl);
                mContext.startActivity(intent);
            } else if (o instanceof Fragment) {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
