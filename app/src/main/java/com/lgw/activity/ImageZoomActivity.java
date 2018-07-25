package com.lgw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.lgw.R;
import com.lgw.widget.PushListView;

public class ImageZoomActivity extends AppCompatActivity {


    private PushListView listView;
    ImageView iv,icon;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                listView.isRefresh(true);//模拟下拉刷新
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        View header = View.inflate(this,R.layout.item_header,null);
        iv = (ImageView) header.findViewById(R.id.layout_header_image);
        icon = (ImageView) header.findViewById(R.id.icon);
        listView = (PushListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
                new String[] {
                        "周一上班",
                        "周二继续上班",
                        "周三打豆豆",
                        "周四打篮球",
                        "周五放假"
                });
        listView.addHeaderView(header);
        listView.setZoomImageView(iv,icon);
        listView.setAdapter(adapter);

        mHandler.sendEmptyMessageDelayed(1,6000);

    }
}
