package com.lgw.weather.model;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lgw.base.Constant;
import com.lgw.bean.Weather;
import com.lgw.callback.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class WeatherModelImpl implements WeatherModel {

    public AMapLocationClient mLocationClient = null;

    private AMapLocationClientOption mLocationOption = null;

    public OnLoadListener mLoadListener;

    @Override
    public void loadLocation(Context context, OnLoadListener loadListener) {
        mLoadListener = loadListener;
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }


    public AMapLocationListener mLocationListener = aMapLocation -> {
        if (null != aMapLocation) {
            if (aMapLocation.getErrorCode() == 0) {
                mLoadListener.OnSuccess(aMapLocation, 0);
                mLocationClient.stopLocation();
                onDestroy();
            } else {
                ToastUtils.showShort("定位失败,错误码" + aMapLocation.getErrorCode());
            }
        } else {
            ToastUtils.showShort("定位失败，loc is null");
        }
    };

    public interface OnLoadListener<T> {
        void OnSuccess(Object object, int type);
    }

    public void onDestroy() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
            LogUtils.d("mLocationClient 销毁了");
        }
    }


    public void getWeatherData(String adcode) {
        OkGo.<Weather>get(Constant.GAODEWEATHERAPI)
                .params("city", adcode)
                .params("key", Constant.key)
                .execute(new JsonCallback<Weather>() {
                    @Override
                    public void onSuccess(Response<Weather> response) {
                        mLoadListener.OnSuccess(response.body(), 1);
                    }
                });
    }
}
