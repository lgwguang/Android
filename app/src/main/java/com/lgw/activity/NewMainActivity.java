package com.lgw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.lgw.R;
import com.lgw.Utils.Base64Util;
import com.lgw.Utils.DESUtils;
import com.lgw.base.BaseActivity;
import com.lgw.base.BaseApplication;
import com.lgw.fragment.HomeFragment;
import com.lgw.fragment.MineFragment;
import com.lgw.fragment.OtherFragment;
import com.lgw.fragment.SettingFragment;
import com.lgw.session.SessionInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class NewMainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    public View  view;
    private FrameLayout fl_content;
    private RadioGroup rg;

    private final String HOME = "home";
    private final String OTHER = "other";
    private String MINE = "mine";
    private String SETTING = "setting";

    @Override
    public int getContentView() {
        return R.layout.activity_new_main;
    }

    @Override
    public void initView() {
        fl_content = findViewById(R.id.fl_content);
        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        onCheckedChanged(rg,R.id.rb_home);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment1 = fm.findFragmentByTag(HOME);
        Fragment fragment2 = fm.findFragmentByTag(OTHER);
        Fragment fragment3 = fm.findFragmentByTag(MINE);
        Fragment fragment4 = fm.findFragmentByTag(SETTING);
        if (fragment1 != null) {
            ft.hide(fragment1);
        }
        if (fragment2 != null) {
            ft.hide(fragment2);
        }
        if (fragment3 != null) {
            ft.hide(fragment3);
        }
        if (fragment4 != null) {
            ft.hide(fragment4);
        }
        switch (checkedId) {
            case R.id.rb_home:
                if (fragment1 == null) {
                    fragment1 = HomeFragment.getInstance();
                    ft.add(R.id.fl_content, fragment1, HOME);
                } else {
                    ft.show(fragment1);
                }
                break;
            case R.id.rb_other:
                if (fragment2 == null) {
                    fragment2 = OtherFragment.getInstance();
                    ft.add(R.id.fl_content, fragment2, OTHER);
                } else {
                    ft.show(fragment2);
                }
                break;
            case R.id.rb_mine:
                if (fragment3 == null) {
                    fragment3 = MineFragment.getInstance();
                    ft.add(R.id.fl_content, fragment3, MINE);
                } else {
                    ft.show(fragment3);
                }
                break;
            case R.id.rb_setting:
                if (fragment4 == null) {
                    fragment4 = SettingFragment.getInstance();
                    ft.add(R.id.fl_content, fragment4, SETTING);
                } else {
                    ft.show(fragment4);
                }
                break;
        }
        ft.commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(rg==null){ return;}
        for (int i = 0; i < rg.getChildCount(); i++) {
            RadioButton mTab = (RadioButton) rg.getChildAt(i);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag((String) mTab.getTag());
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment != null) {
                if (!mTab.isChecked()) {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }


    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BaseApplication.isLogin){
            if(SessionInterface.menuParams!=null){
                sessionInterface.isFragOrAty(SessionInterface.menuParams);
                SessionInterface.menuParams = null;
            }
        }
    }
    public void initStatuBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, Color.parseColor("#FFFFFF"),0);
    }
    public void initStatuBar_hide() {
        StatusBarUtil.setDarkMode(this);
        StatusBarUtil.setColor(this, Color.parseColor("#3F51B5"),0);
    }



    private static final Random RANDOM = new Random();
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

    /** 获取随机数 */
    private String getRndStr(int length) {
        StringBuilder sb = new StringBuilder();
        char ch;
        for (int i = 0; i < length; i++) {
            ch = CHARS.charAt(RANDOM.nextInt(CHARS.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
    private void showEditDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
        View view = View.inflate(mContext,R.layout.dialog_edit,null);
        TextView text1 = view.findViewById(R.id.et_text2);
        Button ok = view.findViewById(R.id.btn_ok);
        Button cancel = view.findViewById(R.id.btn_cancel);
        ok.setOnClickListener(v -> {
            ToastUtils.showShort(text1.getText().toString());
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.setView(view,0,0,0,0);
        dialog.show();
    }
}
