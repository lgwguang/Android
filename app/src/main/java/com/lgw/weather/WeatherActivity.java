package com.lgw.weather;

import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lgw.R;
import com.lgw.base.BaseActivity;
import com.lgw.bean.Weather;
import com.lgw.weather.presenter.WeatherPresenterImpl;
import com.lgw.weather.view.WeatherView;

public class WeatherActivity extends BaseActivity implements WeatherView{

    TextView location;
    private WeatherPresenterImpl presenter;

    @Override
    public int getContentView() {
        return R.layout.activity_weather;
    }

    @Override
    public void initView() {
        location = findViewById(R.id.tv_location);
        presenter = new WeatherPresenterImpl(getApplicationContext(),this);
    }

    @Override
    public void initListener() {

    }


    @Override
    public void updateLocation(AMapLocation aMapLocation) {
        location.setText(aMapLocation.getCountry() + ":" + aMapLocation.getCity() + ":" + aMapLocation.getAdCode() + "\n" + aMapLocation.getAddress());
        LogUtils.d("位置信息："+aMapLocation.getAddress());
        presenter.loadWeatherApi(aMapLocation.getAdCode());
    }

    @Override
    public void updateWeather(Weather weather) {
        if (( "1".equals(weather.status))) {
            ToastUtils.showShort("天气："+weather.lives.get(0).weather+"温度："+weather.lives.get(0).temperature);
        }
    }
}
