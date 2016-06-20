package com.yidian.wemedia.util;

import com.yidian.wemedia.bean.MyField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by zhangbing on 16/5/24.
 */
public class MD5Util {

    public static String MD5(String plainText) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] cipherData = md5.digest(plainText.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte cipher : cipherData) {
            String toHexStr = Integer.toHexString(cipher & 0xff);
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
        }

        return builder.toString();
    }

    public static String MD5Sign(String key, List<MyField> myFieldList, Object obj) throws NoSuchAlgorithmException, IllegalAccessException {

        StringBuffer buffer = new StringBuffer();
        for(MyField myField : myFieldList){
            if(!"sign".equals(myField.getName())) {
                buffer.append(myField.getName() + "=" + myField.getValue(obj));
                buffer.append("&");
            }
        }
        buffer.append("key=" + key);
        String plainText =  buffer.toString();

        return MD5Util.MD5(plainText).toUpperCase();
    }
}
