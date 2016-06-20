package com.bingo.util;

import com.bingo.exception.ErrorCode;
import com.bingo.exception.WeMediaException;
import com.bingo.json.JSONService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * HTTP 核心类，提供基础 WebService 拼接返回值之类的功能
 * Created by Grey on 15/1/13.
 */
public class HttpUtil {
    private static final Logger LOG = Logger.getLogger(HttpUtil.class);

    //正常返回用对象
    public static class SuccessPO<T> {
        public String status;
        public T result;
        public int code;

        SuccessPO(T inRes) {
            this.result = inRes;
            status = "success";
            code = 0;
        }
    }

    //异常返回用对象
    public static class FailPO {
        public String status;
        public String reason;
        public int code;

        FailPO(int code, String message) {
            this.reason = message;
            this.status = "failed";
            this.code = code;
        }
    }

    /**
     * 组装正常返回
     * 注意这里会进行JSON编码，所以要使用对象传递List之类的容器，否则String部分有可能二次编码
     *
     * @param response 返回对象
     * @param body     返回核心内容
     * @param <T>      核心部分类型
     */
    public static <T> void sendSuccess(HttpServletResponse response, T body) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            SuccessPO<T> success = new SuccessPO<T>(body);
            LOG.debug("OUTPUT:" + getStringForLog(success));
            response.getWriter().println(JSONService.getInstance().writeValueAsString(success));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            sentFiled(response, ErrorCode.GeneralError, e.getMessage());
        }
    }

    /**
     * 组装异常返回部分
     *
     * @param response     返回对象
     * @param errorMessage 错误原因
     */
    public static void sentFiled(HttpServletResponse response, ErrorCode errorCode, String errorMessage) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            FailPO fail = new FailPO(errorCode.getCode(), errorMessage);
            LOG.error("OUTPUT: " + getStringForLog(fail));
            response.getWriter().println(JSONService.getInstance().writeValueAsString(fail));
        } catch (Exception e) {
            //严重异常回滚
            LOG.error(e.getMessage(), e);
        }
    }

    public static String getJsonBody(InputStream in) {
        String str;
        try {
            String encode = "utf-8";
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
            StringBuffer sb = new StringBuffer();

            while ((str = reader.readLine()) != null) {
                sb.append(str).append("\n");
            }
            reader.close();
            LOG.info("POST BODY: " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new WeMediaException(ErrorCode.WrongParameters, "Cannot get the POST Body");
        }
    }

    private static String getStringForLog(Object jsonObject) {
        try {
            String str = JSONService.getInstance().writeValueAsString(jsonObject);
//            if (str.length() > 1000) {
//                return str.substring(0, 1000) + "...";
//            } else {
                return str;
//            }
        } catch (Exception e) {
            return null;
        }
    }
}
