package com.lgw.mvp;

import org.json.JSONObject;

public interface ILoginModel extends IModel{

    JSONObject login(String callback);
}
