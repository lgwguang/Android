package com.lgw.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * ================================
 * 作者: lgw
 * 邮箱: 986114497@qq.com
 * 日期: On2018/6/28
 * ================================
 */
public class NameValuePair implements Serializable {
    private final String name;
    private final String value;

    public NameValuePair(String name, String value) {
        if(TextUtils.isEmpty(name)){
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = name;
        this.value = value;
    }


    public String toString() {
        if (this.value == null) {
            return this.name;
        } else {
            int len = this.name.length() + 1 + this.value.length();
            StringBuilder buffer = new StringBuilder(len);
            buffer.append(this.name);
            buffer.append("=");
            buffer.append(this.value);
            return buffer.toString();
        }
    }
}
