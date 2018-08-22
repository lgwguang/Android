package com.lgw.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgw.R;
import com.lgw.Utils.Base64Util;
import com.lgw.Utils.Code;
import com.lgw.Utils.GlideImageLoader;
import com.lgw.Utils.MessageEvent;
import com.lgw.Utils.RSAUtil;
import com.lgw.activity.HandleDrawerActivity;
import com.lgw.activity.SchameFilterActivity;
import com.lgw.activity.TextActivity;
import com.lgw.adapter.MenuAdapter;
import com.lgw.base.BaseApplication;
import com.lgw.bean.MenuItem;
import com.lgw.callback.DialogCallback;
import com.lgw.session.SessionInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private GridView gv;

    private Context mContext;
    private String TAG = "HomeFragment";
    private SessionInterface sessionInterface;
    private Banner banner;
    private TextView btn;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        banner = view.findViewById(R.id.banner);
        gv = view.findViewById(R.id.gv);
        btn = view.findViewById(R.id.btn_json);
        btn.setOnClickListener(this);
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525319864&di=87f476652c96678547ccabbf112076be&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fgamephotolib%2F1410%2F27%2Fc0%2F40170771_1414341013392.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524714908870&di=9d43d35cefbabacdc879733aa7ddc82b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D46de93bfc711728b24208461a095a9bb%2F4610b912c8fcc3ce5423d51d9845d688d43f2038.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524714935901&di=052557513540f3d740eeeb2439c585bb&imgtype=0&src=http%3A%2F%2Fwww.gzlco.com%2Fimggzl%2F214%2F1b6e6520ca474fe4bd3ff728817950717651.jpeg");
        banner.setImages(images);
        banner.start();
        initView();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        ToastUtils.showShort(event.data);
        btn.setText(event.data);
    }


    public void initdata(){
        OkGo.<JSONObject>get("http://www.wanandroid.com/banner/json")
                .tag(this)
                .execute(new DialogCallback<JSONObject>(HomeFragment.this.getActivity()) {
                    @Override
                    public void onSuccess(Response<JSONObject> response) {
                        JSONObject body = response.body();
                        JSONArray data = body.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            
                        }

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
        sessionInterface = new SessionInterface(mContext);
        String menu = ResourceUtils.readAssets2String("menudata.json","UTF-8");
        try {
            JSONObject jsonObject = new JSONObject(menu);
            JSONArray jsonArray = jsonObject.optJSONArray("HomeDisplayList");
            ArrayList<MenuItem> listData = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<MenuItem>>() {
            }.getType());
            MenuAdapter homeAdapter = new MenuAdapter(mContext,listData);
            gv.setAdapter(homeAdapter);
            homeAdapter.setGridViewItemListener((position, o) -> {
                switch (position) {
                    case 0:
//                        showEditDialog();

                        EventBus.getDefault().post("z这是fragment传递过去的");
                        startActivity(new Intent(mContext,TextActivity.class));
                        break;
                    case 1:
                        String str  = Base64Util.base64EncodeStr("北京你好啊  今天是2017年");
                        Log.d(TAG,str);
                        String decodedStr = Base64Util.base64DecodedStr(str);
                        Log.d(TAG,decodedStr);
                        ToastUtils.showShort(decodedStr);

//                        EncryptionTest();

                        System.out.println("========================================");
                        System.out.println("rsa开始啦");
                        // des 字符串加密解密测试
                        try {
                            byte[] data = "GcsSloop中文".getBytes();

                            // 密钥与数字签名获取
//                            Map<String, Object> keyMap = RSAUtil.getKeyPair();
//                            String publicKey = RSAUtil.getKey(keyMap, true);
//                            System.out.println("rsa获取公钥： " + publicKey);
//                            String privateKey = RSAUtil.getKey(keyMap, false);
//                            System.out.println("rsa获取私钥： " + privateKey);
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
                        ThreadUtils.executeByFixed(3, new ThreadUtils.SimpleTask<Object>() {
                            @Override
                            public Object doInBackground() {
                                SystemClock.sleep(1000*2);
                                return "操作完成";
                            }

                            @Override
                            public void onSuccess(Object result) {
                                LogUtils.d("当前线程是否是主线程："+ThreadUtils.isMainThread());
                                ToastUtils.showShort((String)result);
                            }
                        });
                        break;
                    case 5:
//                        ToastUtils.showShort(getRndStr(5));
                        break;
                    case 6:
                        Code a = new Code();
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


    @Override
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
    }

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
        EventBus.getDefault().unregister(this);
    }
}
