package com.lgw.Utils;

import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kotlin.jvm.Synchronized;

public class RxPermissionUtil {

    private static RxPermissions rxPermissions ;

    private RxPermissionUtil(){}

    public static RxPermissions  getInstance(FragmentActivity act){
        if(rxPermissions == null){
            synchronized(RxPermissionUtil.class){
                if(rxPermissions == null){
                    rxPermissions = new RxPermissions(act);
                }
            }
        }
        return rxPermissions;
    }
}
