package com.lgw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lgw.R;
import com.lgw.base.BaseActivity;
import com.lgw.fragment.HomeFragment;
import com.lgw.fragment.MineFragment;
import com.lgw.fragment.OtherFragment;
import com.lgw.fragment.SettingFragment;

public class Main2Activity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private Class<?> fragmentArray[] = {
            HomeFragment.class, OtherFragment.class,
            MineFragment.class, SettingFragment.class};

    private int mImageViewArray[] = {
            R.drawable.shelfselector, R.drawable.bookstoreselector,
            R.drawable.bookmomentselector, R.drawable.userselector};

    private String[] mTextviewArray;

    @Override
    public int getContentView() {
        return R.layout.activity_main2;
    }

    @Override
    public void initView() {
        mTextviewArray = getResources().getStringArray(R.array.items);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // 利用getSupportFragmentManager()获取一个FragmentManager
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 添加tab名称和图标
        for (int i = 0; i < fragmentArray.length; i++) {
            TabHost.TabSpec taSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            mTabHost.addTab(taSpec, fragmentArray[i], null);
        }
        mTabHost.setCurrentTab(0);
        //去掉分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);

        Fragment f = getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());

        if (f != null && f instanceof OtherFragment) {
            f.onActivityResult(arg0, arg1, arg2);
        }
    }

    public FragmentTabHost getmTabHost() {
        return mTabHost;
    }
    private View getTabItemView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);
        return view;
    }


    private FragmentBackClick listener;

    public void setFBackListener(FragmentBackClick listener) {
        this.listener = listener;
    }

    public interface FragmentBackClick {
        boolean onBackClick();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (listener != null) {
                boolean isReturn = listener.onBackClick();
                if (isReturn)
                    return true ;
            }
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initStatuBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, Color.parseColor("#FFFFFF"),0);
    }
    public void initStatuBar_hide() {
        StatusBarUtil.setDarkMode(this);
        StatusBarUtil.setColor(this, Color.parseColor("#3F51B5"),0);
    }
}
