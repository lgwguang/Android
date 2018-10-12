package com.lgw.model;

import com.lgw.bean.UserBean;

public interface UserModel {

    void setSid(int cid);

    UserBean load(int sid);

    void setUsername(String username);

    void setUserpwd(String userpwd);
}
