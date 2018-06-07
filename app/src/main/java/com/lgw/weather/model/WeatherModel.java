package com.lgw.weather.model;

import android.content.Context;

public interface WeatherModel {

    void loadLocation(Context context, WeatherModelImpl.OnLoadListener loadListener);
}
