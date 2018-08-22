package com.lgw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lgw.R;

public class JsbridgeActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "JsbridgeActivity";
    private BridgeWebView mWebView;
    private Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsbridge);
        mWebView = findViewById(R.id.webView);
        mBtnOk = findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        mWebView.loadUrl("file:///android_asset/demo.html");;
        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //js数据传到java
                ToastUtils.showShort(data);
                //返回到网页做出相应
                function.onCallBack(data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //java数据传递到js
        mWebView.callHandler("test", "我是java本地", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i(TAG, "reponse data from js " + data);
            }
        });
    }
}
