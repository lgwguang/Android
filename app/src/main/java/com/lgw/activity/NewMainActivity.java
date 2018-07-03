package com.lgw.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SPUtils;
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
                    case Integer.MAX_VALUE:
                        sessionInterface.isFragOrAty((MenuItem) o);
                        break;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
