package com.bingo.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by zhangbing on 16/6/20.
 */
public class IPUtil {

    public static String getIP(HttpServletRequest req) {
        //获取所有的请求头
        Enumeration<String> reqHeadInfos = req.getHeaderNames();
        String res = "";
        while (reqHeadInfos.hasMoreElements()) {
            String headName = reqHeadInfos.nextElement();
            if (headName.toLowerCase().equals("cdn-src-ip")) {
                res += "cdn-src-ip " + req.getHeader(headName) + ";";
            } else if (headName.toLowerCase().equals("x-forwarded-for")) {
                res += "x-forwarded-for " + req.getHeader(headName) + ";";
            } else if (headName.toLowerCase().equals("x-real-ip")) {
                res += "x-real-ip " + req.getHeader(headName) + ";";
            } else if (headName.toLowerCase().equals("clientip")) {
                res += "clientip " + req.getHeader(headName) + ";";
            }
        }
        if (res.equals("")) {
            return "UNKNOWN";
        } else {
            return res;
        }
    }
}
