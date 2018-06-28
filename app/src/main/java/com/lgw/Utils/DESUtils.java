package com.lgw.Utils;

import android.util.Base64;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtils {

    private static final String TAG = "<加解密工具>";

    private static final String CHARSET = "UTF-8";
    private static final String DES = "DES";
    public static String KEY_Default = "12345678";
    private String myKey = KEY_Default;
    private SecretKey secretkey = null;

    private DESUtils() {
    }

    private static DESUtils instance;

    public static synchronized DESUtils getInstance() {
        if (instance == null)
            instance = new DESUtils();
        return instance;
    }

    private Key getKey() {
        byte[] bb = null;
        try {
            bb = myKey.getBytes(CHARSET);
            secretkey = new SecretKeySpec(bb, DES);
        } catch (Exception e) {
            Log.i(TAG, "<获得密钥失败>" + e.toString());
        }
        return secretkey;
    }

    public DESUtils setKEY(String kEY) {
        myKey = kEY;
        return instance;
    }

    public String encrypt(String source) {
        String s = null;
        byte[] target = null;
        try {
            byte[] center = source.getBytes(CHARSET);
            Key key = getKey();
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            target = cipher.doFinal(center);
            s = Base64.encodeToString(target, Base64.DEFAULT);
        } catch (Exception e) {
            Log.i(TAG, "<DES加密错误>" + e.toString());
        }
        return s;
    }

    public String decrypt(String source) {
        String s = null;
        byte[] dissect = null;
        try {
            byte[] center = Base64.decode(source.getBytes(CHARSET), Base64.DEFAULT);
            Key key = getKey();
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            dissect = cipher.doFinal(center);
            s = new String(dissect, CHARSET);
        } catch (Exception e) {
            Log.i(TAG, "<DES解密错误>" + e.toString());
        }
        return s;
    }
}
