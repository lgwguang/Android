package com.lgw.activity;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgw.R;
import com.lgw.adapter.HomeAdapter;
import com.lgw.adapter.OnViewItemListener;
import com.lgw.base.BaseActivity;
import com.lgw.bean.Home;

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
        String menu = ResourceUtils.readAssets2String("menudata01.json","UTF-8");
        LogUtils.d(menu);
        try {
            JSONObject jsonObject = new JSONObject(menu);
            JSONArray jsonArray = jsonObject.optJSONArray("HomeDisplayList");
            Gson gson = new Gson();
            ArrayList<Home> listData = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Home>>() {
            }.getType());
            HomeAdapter homeAdapter = new HomeAdapter(mContext,listData);
            gv.setAdapter(homeAdapter);
            homeAdapter.setGridViewItemListener(new OnViewItemListener() {
                @Override
                public void viewItemClick(int position, Object o) {
                    switch (position) {
                        case 0:
                            showEditDialog();
                            break;
                    }
                }
            });


//            new OnViewItemListener() {
//                @Override
//                public void viewItemClick(int position, Object o) {
//                    switch (position) {
//                        case 0:
//                            showEditDialog();
//                            break;
//                    }
//                }
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
}
