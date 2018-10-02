package com.lgw.Utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;

import java.util.Map;

public class OkGoUtil {

    public static <T> void getRequets(String url, Object tag, Map<String, String> map, Callback<T> callback) {
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }
    public static <T> void postRequest(String url, Object tag, Map<String, String> map, Callback<T> callback) {
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }

}
