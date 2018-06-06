package com.lgw.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.lgw.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sample_text.text = stringFromJNI()

        tianqi.setOnClickListener({v->
            ActivityUtils.startActivity(WeatherActivity::class.java)
        })
    }

    external fun stringFromJNI(): String
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
