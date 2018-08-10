package com.lgw.activity;

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
    private ViewGroup viewGroup;
    private FrameLayout fl_content;
    private RadioGroup rg;
    private ArrayList<String> list;
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
        rg.check(R.id.rb_home);


//        gv = findViewById(R.id.gv);
//        String menu = ResourceUtils.readAssets2String("menudata.json","UTF-8");
//        try {
//            JSONObject jsonObject = new JSONObject(menu);
//            JSONArray jsonArray = jsonObject.optJSONArray("HomeDisplayList");
//            Gson gson = new Gson();
//            ArrayList<MenuItem> listData = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<MenuItem>>() {
//            }.getType());
//            MenuAdapter homeAdapter = new MenuAdapter(mContext,listData);
//            gv.setAdapter(homeAdapter);
//            homeAdapter.setGridViewItemListener((position, o) -> {
//                switch (position) {
//                    case 0:
//                        showEditDialog();
//
//                        break;
//                    case 1:
//                        String str  = Base64Util.base64EncodeStr("北京你好啊  今天是2017年");
//                        Log.d(TAG,str);
//                        String decodedStr = Base64Util.base64DecodedStr(str);
//                        Log.d(TAG,decodedStr);
//                        ToastUtils.showShort(decodedStr);
//
////                        EncryptionTest();
//
//                        System.out.println("========================================");
//                        System.out.println("rsa开始啦");
//                        // des 字符串加密解密测试
//                        try {
//                            byte[] data = "GcsSloop中文".getBytes();
//
//                            // 密钥与数字签名获取
////                            Map<String, Object> keyMap = RSAUtil.getKeyPair();
////                            String publicKey = RSAUtil.getKey(keyMap, true);
////                            System.out.println("rsa获取公钥： " + publicKey);
////                            String privateKey = RSAUtil.getKey(keyMap, false);
////                            System.out.println("rsa获取私钥： " + privateKey);
//                            final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2HEP0E2uK/u+7C3YnY+z/6CdRj8ryYKuFJF8F\n" +
//                                    "    gEPJLhF2WkpLL+xqHixA2dP4wTzNwh5r918SrY3eTM9yUW5l2i03l49cZkVc29Gwzv/EVkLTB0dF\n" +
//                                    "    QgdwFz4l+ThfNHxA+swoJFn9lRFZxNaTNt/MXOh9F+/KuuOgCqESiSLrIwIDAQAB";
//                            final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALYcQ/QTa4r+77sLdidj7P/oJ1GP\n" +
//                                    "    yvJgq4UkXwWAQ8kuEXZaSksv7GoeLEDZ0/jBPM3CHmv3XxKtjd5Mz3JRbmXaLTeXj1xmRVzb0bDO\n" +
//                                    "    /8RWQtMHR0VCB3AXPiX5OF80fED6zCgkWf2VEVnE1pM238xc6H0X78q646AKoRKJIusjAgMBAAEC\n" +
//                                    "    gYEAtK6goUavWc4NDpiVlwlmuJSehibTpd1R8ByhBnU3TCCgBLwaoDShLLxZDdLRpCodaMxjoJ1j\n" +
//                                    "    v8VgT6IH5JV0oH7Z4UTZFJwTXV/eAhmv55iu8eCxlcv5F3S6865JkXXP5Pea0Hldm1eVHGmfoIE9\n" +
//                                    "    LJuVwflR79jIHeHqs6BqUdkCQQDdjFvkAsFmo3mOgkUm5UtYx5aEPIwkYspXYCVF2F1ZOiHY3qYe\n" +
//                                    "    g2BicDEXdCzmsYIe6XXPp0bO+7EG8HvXKLNvAkEA0m3rgX3yKiYc0VWaE22JKEp7QCAqmHztukhn\n" +
//                                    "    gicfHYKHa2j6XdDU5xFJYCyjc9UNkqlS+uWHZSXtlMjCltHZjQJAFSe79o/Uy+o8R0FlPQuUIEwe\n" +
//                                    "    sU3ey/KrA6Dorjy03TpR1RWdozhEUeIIrgan6vd0R2Nfno6IkmcyRklcafU2/QJBAMKgJe1L1tpD\n" +
//                                    "    C+5VtT741Z3tXZeO6LbG/X2JECtiEbZSjvu6Sa7RwsjAxVPtlXRB/t7CO1yRRKw560bsdIXYK9UC\n" +
//                                    "    QQCLY6+gB6QKRnqemP8CB9XjzjljxJT2syprtVEp2XWdYtWnvtSk4XnyK0+Q04bNaUHCGL8DAsjM\n" +
//                                    "    PijC8zj5ScWM";
//                            // 公钥加密私钥解密
//                            byte[] rsaPublic =
//                                    RSAUtil.rsa(data, publicKey, RSAUtil.RSA_PUBLIC_ENCRYPT);
//                            System.out.println("rsa公钥加密： " + new String(rsaPublic));
//                            System.out.println("rsa私钥解密： " + new String(
//                                    RSAUtil.rsa(rsaPublic, privateKey, RSAUtil.RSA_PRIVATE_DECRYPT)));
//
//                            // 私钥加密公钥解密
//                            byte[] rsaPrivate =
//                                    RSAUtil.rsa(data, privateKey, RSAUtil.RSA_PRIVATE_ENCRYPT);
//                            System.out.println("rsa私钥加密： " + new String(rsaPrivate));
//                            System.out.println("rsa公钥解密： " + new String(
//                                    RSAUtil.rsa(rsaPrivate, publicKey, RSAUtil.RSA_PUBLIC_DECRYPT)));
//
//                            // 私钥签名及公钥签名校验
//                            String signStr = RSAUtil.sign(rsaPrivate, privateKey);
//                            System.out.println("rsa数字签名生成： " + signStr);
//                            System.out.println("rsa数字签名校验： " + RSAUtil.verify(rsaPrivate, publicKey, signStr));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        break;
//                    case 2:
//                        Intent intent = new Intent(this,SchameFilterActivity.class);
//                        intent.setData(Uri.parse("https://www.baidu.com/"));
//                        startActivity(intent);
//                        break;
//                    case 3:
//                        startActivity(new Intent(this,HandleDrawerActivity.class));
//                        break;
//                    case 4:
//                        ThreadUtils.executeByFixed(3, new ThreadUtils.SimpleTask<Object>() {
//                            @Override
//                            public Object doInBackground() {
//                                SystemClock.sleep(1000*2);
//                                return "操作完成";
//                            }
//
//                            @Override
//                            public void onSuccess(Object result) {
//                                LogUtils.d("当前线程是否是主线程："+ThreadUtils.isMainThread());
//                                ToastUtils.showShort((String)result);
//                            }
//                        });
//                        break;
//                    case 5:
//                        ToastUtils.showShort(getRndStr(5));
//                        break;
//                    case 6:
//                        Code a = new Code();
//                    case 7:
//                    case 8:
//                    case 14:
//                    case Integer.MAX_VALUE:
//                        sessionInterface.isFragOrAty((MenuItem) o);
//                        break;
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

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
                    fragment1 = new HomeFragment();
                    ft.add(R.id.fl_content, fragment1, HOME);
                } else {
                    ft.show(fragment1);
                }
                break;
            case R.id.rb_other:
                if (fragment2 == null) {
                    fragment2 = new OtherFragment();
                    ft.add(R.id.fl_content, fragment2, OTHER);
                } else {
                    ft.show(fragment2);
                }
                break;
            case R.id.rb_mine:
                if (fragment3 == null) {
                    fragment3 = new MineFragment();
                    ft.add(R.id.fl_content, fragment3, MINE);
                } else {
                    ft.show(fragment3);
                }
                break;
            case R.id.rb_setting:
                if (fragment4 == null) {
                    fragment4 = new SettingFragment();
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
    private static final Random RANDOM = new Random();
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    /**
     * 针对URL进行签名，关于这几个参数的作用，详细请看
     * http://www.cnblogs.com/bestzrz/archive/2011/09/03/2164620.html
     */
    /*private void sign(HttpParams params) {
        params.put("nonce", getRndStr(6 + RANDOM.nextInt(8)));
        params.put("timestamp", "" + (System.currentTimeMillis() / 1000L));
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : params.urlParamsMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get(0));
        }
        for (Map.Entry<String, String> entry : getSortedMapByKey(map).entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        String sign = MD5Utils.encode(sb.toString());
        params.put("sign", sign);
    }*/

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
    /** 按照key的自然顺序进行排序，并返回 */
    private Map<String, String> getSortedMapByKey(Map<String, String> map) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        };
        Map<String, String> treeMap = new TreeMap<>(comparator);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        return treeMap;
    }
    private void EncryptionTest(){
        String str = "北京你好123";
        String encrypt = DESUtils.getInstance().setKEY(DESUtils.KEY_Default).encrypt(str);
        SPUtils.getInstance().put("DESE",encrypt);
        String decrypt = DESUtils.getInstance().setKEY(DESUtils.KEY_Default).decrypt(encrypt);
        SPUtils.getInstance().put("DESD",decrypt);

        String encodeStr = Base64Util.base64EncodeStr(str);
        SPUtils.getInstance().put("Base64E",encodeStr);
        String decodedStr = Base64Util.base64DecodedStr(encodeStr);
        SPUtils.getInstance().put("Base64D",decodedStr);
        ToastUtils.showShort("=====");
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

}
