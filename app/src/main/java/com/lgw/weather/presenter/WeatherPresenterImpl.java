package com.lgw.weather.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.lgw.bean.Weather;
import com.lgw.weather.model.WeatherModel;
import com.lgw.weather.model.WeatherModelImpl;
import com.lgw.weather.view.WeatherView;
import org.jetbrains.annotations.NotNull;


public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.OnLoadListener {

    private WeatherView mWeatherView;
    private WeatherModelImpl model;

    public WeatherPresenterImpl(Context context, WeatherView weatherView) {
        mWeatherView = weatherView;
        model = new WeatherModelImpl();
        model.loadLocation(context, this);
    }

    public void loadWeatherApi(String adcode){
        model.getWeatherData(adcode);
    }

    @Override
    public void OnSuccess(Object object, int type) {
        switch (type) {
            case 0:
                mWeatherView.updateLocation((AMapLocation)object);
                break;
            case 1:
                mWeatherView.updateWeather((Weather)object);
                break;
        }
    }
}
