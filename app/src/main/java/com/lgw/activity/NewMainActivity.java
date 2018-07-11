package com.lgw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgw.R;
import com.lgw.Utils.Base64Util;
import com.lgw.Utils.DESUtils;
import com.lgw.adapter.MenuAdapter;
import com.lgw.base.BaseActivity;
import com.lgw.base.BaseApplication;
import com.lgw.bean.MenuItem;
import com.lgw.session.SessionInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class NewMainActivity extends BaseActivity {

    private GridView gv;

    @Override
    public int getContentView() {
        return R.layout.activity_new_main;
    }

    @Override
    public void initView() {
        gv = findViewById(R.id.gv);
        String menu = ResourceUtils.readAssets2String("menudata.json","UTF-8");
        try {
            JSONObject jsonObject = new JSONObject(menu);
            JSONArray jsonArray = jsonObject.optJSONArray("HomeDisplayList");
            Gson gson = new Gson();
            ArrayList<MenuItem> listData = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<MenuItem>>() {
            }.getType());
            MenuAdapter homeAdapter = new MenuAdapter(mContext,listData);
            gv.setAdapter(homeAdapter);
            homeAdapter.setGridViewItemListener((position, o) -> {
                switch (position) {
                    case 0:
                        showEditDialog();
                        break;
                    case 1:
                        EncryptionTest();
                        break;
                    case 2:
                        Intent intent = new Intent(this,SchameFilterActivity.class);
                        intent.setData(Uri.parse("https://www.baidu.com/"));
                        startActivity(intent);
                        break;
                    case 3:
                        startActivity(new Intent(this,HandleDrawerActivity.class));
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
                        ToastUtils.showShort(getRndStr(5));
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                    case Integer.MAX_VALUE:
                        sessionInterface.isFragOrAty((MenuItem) o);
                        break;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
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
