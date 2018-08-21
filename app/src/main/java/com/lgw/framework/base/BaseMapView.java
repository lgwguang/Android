package com.lgw.framework.base;

import android.content.Context;
import android.os.Bundle;

import com.lgw.framework.IMapFactory;

public abstract class BaseMapView implements IMapFactory {

    private Context context;

    public BaseMapView(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){

    }
}
