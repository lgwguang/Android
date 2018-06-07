package com.lgw.weather.model;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

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
                mLoadListener.OnSuccess(aMapLocation);
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
        void OnSuccess(AMapLocation mAMapLocation);
    }

    public void onDestroy() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
            LogUtils.d("mLocationClient 销毁了");
        }
    }
}
