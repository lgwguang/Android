package com.lgw.mvp;

import com.lgw.Utils.OkGoUtil;
import com.lgw.callback.JsonCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

public class LoginModel implements ILoginModel {


    @Override
    public void onDestroy() {

    }
    JSONObject reslut;
    @Override
    public JSONObject login(String callback) {
        OkGoUtil.postRequest("Https://www.baidu.com",null,null,new JsonCallback<JSONObject>(){


            @Override
            public void onSuccess(Response<JSONObject> response) {
                reslut = response.body();
            }
        });
        return reslut;
    }
}
