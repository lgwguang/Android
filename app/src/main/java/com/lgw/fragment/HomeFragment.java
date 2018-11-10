package com.lgw.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.lgw.R;
import com.lgw.Utils.Base64Util;
import com.lgw.Utils.GlideImageLoader;
import com.lgw.Utils.MessageEvent;
import com.lgw.Utils.OkGoUtil;
import com.lgw.Utils.PermissionUtil;
import com.lgw.Utils.RSAUtil;
import com.lgw.Utils.RxPermissionUtil;
import com.lgw.activity.AppBarActivity;
import com.lgw.activity.HandleDrawerActivity;
import com.lgw.activity.Main2Activity;
import com.lgw.activity.MainActivity;
import com.lgw.activity.NewMainActivity;
import com.lgw.activity.SchameFilterActivity;
import com.lgw.activity.TextActivity;
import com.lgw.adapter.HomeAdapter;
import com.lgw.adapter.MenuAdapter;
import com.lgw.base.BaseApplication;
import com.lgw.bean.Ad;
import com.lgw.bean.BaseResponse;
import com.lgw.bean.MenuItem;
import com.lgw.callback.DialogCallback;
import com.lgw.session.SessionInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;


public class HomeFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "HomeFragment";

    private Context mContext;
    private SessionInterface sessionInterface;

    Banner banner;
    AppBarLayout appbarlayout;
    RelativeLayout rl_show,rl_hide;
    RecyclerView recyclerview;
    private View rootView;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private static HomeFragment homeFragment;

    public static HomeFragment getInstance(){
        if(homeFragment == null){
            synchronized(HomeFragment.class){
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                }
            }
        }
        return homeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            banner = rootView.findViewById(R.id.banner);
            appbarlayout = rootView.findViewById(R.id.appbarlayout);
            rl_show = rootView.findViewById(R.id.rl_show);
            rl_hide = rootView.findViewById(R.id.rl_hide);
            recyclerview = rootView.findViewById(R.id.recyclerview);
            initView();
            ToastUtils.showShort("rootView");
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ToastUtils.showShort("onCreateView");
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        ToastUtils.showShort(event.data);
    }


    public void initdata(){

        OkGoUtil.getRequets("http://www.wanandroid.com/banner/json", this, null, new DialogCallback<BaseResponse<List<Ad>>>((Main2Activity)mContext) {

            @Override
            public void onSuccess(Response<BaseResponse<List<Ad>>> response) {
                BaseResponse<List<Ad>> body = response.body();
                List<Ad> data = body.getData();
                for (Ad datum : data) {
                    String imagePath = datum.getImagePath();
                    images.add(imagePath);
                    titles.add(datum.getTitle());
                }
                banner.setImages(images);
                banner.setBannerTitles(titles);
                banner.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
        if(BaseApplication.isLogin){
            if(SessionInterface.menuParams!=null){
                sessionInterface.isFragOrAty(SessionInterface.menuParams);
                SessionInterface.menuParams = null;
            }
        }
    }

    private void initView() {
        EventBus.getDefault().register(this);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext,4));
        appbarlayout.addOnOffsetChangedListener(this);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        ((Main2Activity)mContext).initStatuBar();
        sessionInterface = new SessionInterface(mContext);
        String menu = ResourceUtils.readAssets2String("menudata.json","UTF-8");
        try {
            JSONObject jsonObject = new JSONObject(menu);
            JSONArray jsonArray = jsonObject.optJSONArray("MoreDisplayList");
            ArrayList<MenuItem> listData = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<MenuItem>>() {
            }.getType());
            HomeAdapter homeAdapter = new HomeAdapter(listData);
            recyclerview.setAdapter(homeAdapter);
            homeAdapter.setGridViewItemListener((position, o) -> {
                switch (position) {
                    case 0:
//                        showEditDialog();
                        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                            @Override
                            public void onRequestPermissionSuccess() {
                                ToastUtils.showShort("权限请求成功");
                                EventBus.getDefault().postSticky("z这是fragment传递过去的");
                                startActivity(new Intent(mContext,TextActivity.class));
                            }

                            @Override
                            public void onRequestPermissionFailure(List<String> permissions) {
                                ToastUtils.showShort("权限请求失败");
                            }

                            @Override
                            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                                ToastUtils.showShort("权限请求失败并且不再询问");
                            }
                        },new RxPermissions(HomeFragment.this),RxErrorHandler.builder().with(mContext).responseErrorListener(new ResponseErrorListener() {
                            @Override
                            public void handleResponseError(Context context, Throwable t) {
                                t.printStackTrace();
                            }
                        }).build());

                        break;
                    case 1:
                        String str  = Base64Util.base64EncodeStr("北京你好啊  今天是2017年");
                        Log.d(TAG,str);
                        String decodedStr = Base64Util.base64DecodedStr(str);
                        Log.d(TAG,decodedStr);
                        ToastUtils.showShort(decodedStr);
                        System.out.println("========================================");
                        System.out.println("rsa开始啦");
                        // des 字符串加密解密测试
                        try {
                            byte[] data = "GcsSloop中文".getBytes();
                            final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2HEP0E2uK/u+7C3YnY+z/6CdRj8ryYKuFJF8F\n" +
                                    "    gEPJLhF2WkpLL+xqHixA2dP4wTzNwh5r918SrY3eTM9yUW5l2i03l49cZkVc29Gwzv/EVkLTB0dF\n" +
                                    "    QgdwFz4l+ThfNHxA+swoJFn9lRFZxNaTNt/MXOh9F+/KuuOgCqESiSLrIwIDAQAB";
                            final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALYcQ/QTa4r+77sLdidj7P/oJ1GP\n" +
                                    "    yvJgq4UkXwWAQ8kuEXZaSksv7GoeLEDZ0/jBPM3CHmv3XxKtjd5Mz3JRbmXaLTeXj1xmRVzb0bDO\n" +
                                    "    /8RWQtMHR0VCB3AXPiX5OF80fED6zCgkWf2VEVnE1pM238xc6H0X78q646AKoRKJIusjAgMBAAEC\n" +
                                    "    gYEAtK6goUavWc4NDpiVlwlmuJSehibTpd1R8ByhBnU3TCCgBLwaoDShLLxZDdLRpCodaMxjoJ1j\n" +
                                    "    v8VgT6IH5JV0oH7Z4UTZFJwTXV/eAhmv55iu8eCxlcv5F3S6865JkXXP5Pea0Hldm1eVHGmfoIE9\n" +
                                    "    LJuVwflR79jIHeHqs6BqUdkCQQDdjFvkAsFmo3mOgkUm5UtYx5aEPIwkYspXYCVF2F1ZOiHY3qYe\n" +
                                    "    g2BicDEXdCzmsYIe6XXPp0bO+7EG8HvXKLNvAkEA0m3rgX3yKiYc0VWaE22JKEp7QCAqmHztukhn\n" +
                                    "    gicfHYKHa2j6XdDU5xFJYCyjc9UNkqlS+uWHZSXtlMjCltHZjQJAFSe79o/Uy+o8R0FlPQuUIEwe\n" +
                                    "    sU3ey/KrA6Dorjy03TpR1RWdozhEUeIIrgan6vd0R2Nfno6IkmcyRklcafU2/QJBAMKgJe1L1tpD\n" +
                                    "    C+5VtT741Z3tXZeO6LbG/X2JECtiEbZSjvu6Sa7RwsjAxVPtlXRB/t7CO1yRRKw560bsdIXYK9UC\n" +
                                    "    QQCLY6+gB6QKRnqemP8CB9XjzjljxJT2syprtVEp2XWdYtWnvtSk4XnyK0+Q04bNaUHCGL8DAsjM\n" +
                                    "    PijC8zj5ScWM";
                            // 公钥加密私钥解密
                            byte[] rsaPublic =
                                    RSAUtil.rsa(data, publicKey, RSAUtil.RSA_PUBLIC_ENCRYPT);
                            System.out.println("rsa公钥加密： " + new String(rsaPublic));
                            System.out.println("rsa私钥解密： " + new String(
                                    RSAUtil.rsa(rsaPublic, privateKey, RSAUtil.RSA_PRIVATE_DECRYPT)));

                            // 私钥加密公钥解密
                            byte[] rsaPrivate =
                                    RSAUtil.rsa(data, privateKey, RSAUtil.RSA_PRIVATE_ENCRYPT);
                            System.out.println("rsa私钥加密： " + new String(rsaPrivate));
                            System.out.println("rsa公钥解密： " + new String(
                                    RSAUtil.rsa(rsaPrivate, publicKey, RSAUtil.RSA_PUBLIC_DECRYPT)));

                            // 私钥签名及公钥签名校验
                            String signStr = RSAUtil.sign(rsaPrivate, privateKey);
                            System.out.println("rsa数字签名生成： " + signStr);
                            System.out.println("rsa数字签名校验： " + RSAUtil.verify(rsaPrivate, publicKey, signStr));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        Intent intent = new Intent(mContext,SchameFilterActivity.class);
                        intent.setData(Uri.parse("https://www.baidu.com/"));
                        startActivity(intent);
                        break;
                    case 3:
                        startActivity(new Intent(mContext,HandleDrawerActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext,AppBarActivity.class));
                        break;
                    case 5:
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 14:
                    case Integer.MAX_VALUE:
                        sessionInterface.isFragOrAty((MenuItem) o);
                        break;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


   /* @Override
    public void onClick(View v) {
        StringBuilder sb = new StringBuilder();
        String menu = ResourceUtils.readAssets2String("temp.json","UTF-8");
        try {
            JSONArray jsonArray = new JSONArray(menu);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String groupId = jsonObject.optString("groupId");
                if(sb.toString().contains(groupId)){
                    continue;
                }
                sb.append("groupId:").append(groupId).append("groupName:").append(jsonObject.optString("groupName"));

            }
            LogUtils.d(sb.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d("=======onStop=======");
        banner.releaseBanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("=======onDestroy=======");
        images = null;
        titles = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(verticalOffset<=0){
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            if(Math.abs(verticalOffset)==totalScrollRange){
                rl_show.setVisibility(View.GONE);
                rl_hide.setVisibility(View.VISIBLE);
                ((Main2Activity)mContext).initStatuBar();
            }else{
                rl_show.setVisibility(View.VISIBLE);
                rl_hide.setVisibility(View.GONE);
                ((Main2Activity)mContext).initStatuBar_hide();
            }
        }
    }

}
