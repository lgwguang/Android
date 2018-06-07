package com.lgw.weather.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.lgw.weather.model.WeatherModel;
import com.lgw.weather.model.WeatherModelImpl;
import com.lgw.weather.view.WeatherView;
import org.jetbrains.annotations.NotNull;


public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.OnLoadListener {

    private WeatherView mWeatherView;

    public WeatherPresenterImpl(Context context, WeatherView weatherView) {
        mWeatherView = weatherView;
        WeatherModel model = new WeatherModelImpl();
        model.loadLocation(context, this);
    }

    @Override
    public void OnSuccess(@NotNull AMapLocation mAMapLocation) {

        mWeatherView.updateLocation(mAMapLocation);
    }
}
