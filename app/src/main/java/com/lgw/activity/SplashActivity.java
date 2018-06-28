package com.lgw.activity;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lgw.R;
import com.lgw.adapter.GuideViewPagerAdapter;
import com.lgw.api.Constant;
import com.lgw.base.BaseActivity;
import com.lgw.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashActivity extends BaseActivity {

    ViewPager vpPager;
    Button btnGo;

    private ArrayList<View> views;

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }
    @Override
    public void initView() {
        vpPager = findViewById(R.id.vp_pager);
        btnGo = findViewById(R.id.btn_go);
        initAuthority();
        if (SPUtils.getInstance(Constant.SP_NAME).getBoolean(Constant.KEY_FIRST_SPLASH, true)) {
            initSplashView();
            initSplashListener();
        }else{
            startActivity(GuideActivity.class);
            finish();
        }
    }

    private void initAuthority() {
        String menu = ResourceUtils.readAssets2String("menudata.json","UTF-8");
        try {
            JSONObject jsonObject = new JSONObject(menu);
            JSONArray jsonArray = jsonObject.optJSONArray("AuthorityList");
            if (jsonArray != null) {
                HashMap<String, String> AuthorityMap = new HashMap<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.optJSONObject(i);
                    AuthorityMap.put(obj.optString("ActionId"),
                            obj.optString("ActionId"));
                }
                BaseApplication.AuthorityList = AuthorityMap;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initListener() {
        btnGo.setOnClickListener(v -> {
            startActivity(NewMainActivity.class);
            SPUtils.getInstance(Constant.SP_NAME).put(Constant.KEY_FIRST_SPLASH, false);
            finish();
        });
    }

    private void initSplashView() {
        views = new ArrayList<>();
        View view1 = View.inflate(mContext, R.layout.activity_splash_view, null);
        View view2 = View.inflate(mContext, R.layout.activity_splash_view, null);
        View view3 = View.inflate(mContext, R.layout.activity_splash_view, null);
        View view4 = View.inflate(mContext, R.layout.activity_splash_view, null);
        View view5 = View.inflate(mContext, R.layout.activity_splash_view, null);
        View view6 = View.inflate(mContext, R.layout.activity_splash_view, null);

        ((ImageView) view1.findViewById(R.id.iv_image)).setImageResource(R.drawable.guide_01);
        ((ImageView) view2.findViewById(R.id.iv_image)).setImageResource(R.drawable.guide_02);
        ((ImageView) view3.findViewById(R.id.iv_image)).setImageResource(R.drawable.guide_03);
        ((ImageView) view4.findViewById(R.id.iv_image)).setImageResource(R.drawable.guide_04);
        ((ImageView) view5.findViewById(R.id.iv_image)).setImageResource(R.drawable.guide_05);
        ((ImageView) view6.findViewById(R.id.iv_image)).setImageResource(R.drawable.guide_06);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
    }

    private void initSplashListener() {
        GuideViewPagerAdapter guideViewPagerAdapter = new GuideViewPagerAdapter(views);
        vpPager.setAdapter(guideViewPagerAdapter);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position + 1 == vpPager.getAdapter().getCount()) {
                    if (btnGo.getVisibility() != View.VISIBLE) {
                        btnGo.setVisibility(View.VISIBLE);
                        btnGo.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                    }
                } else {
                    if (btnGo.getVisibility() != View.GONE) {
                        btnGo.setVisibility(View.GONE);
                        btnGo.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
