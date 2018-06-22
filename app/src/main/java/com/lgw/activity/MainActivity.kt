package com.lgw.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.lgw.R
import com.lgw.base.BaseApplication
import com.lgw.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtils.d("主页面")
        sample_text.text = stringFromJNI()

        tianqi.setOnClickListener{v->
            ActivityUtils.startActivity(WeatherActivity::class.java)
        }
        btn_scroll.setOnClickListener{v ->
            startActivity(Intent(this,ScrollingActivity::class.java))
        }


        siv.isChecked = true
    }

    override fun onResume() {
        super.onResume()
        ToastUtils.showLong(BaseApplication.a+":");
    }

    external fun stringFromJNI(): String
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
