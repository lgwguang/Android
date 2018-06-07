package com.lgw.weather.view;

import com.amap.api.location.AMapLocation;
import com.lgw.bean.Weather;

public interface WeatherView {

    void updateLocation(AMapLocation mAMapLocation);

    void updateWeather(Weather weather);
}
