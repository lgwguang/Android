package com.lgw.framework;

import android.os.Bundle;

public interface IMapFactory {

    IMapView getMapView();

    void onCreate(Bundle saveInstanceState);
}
