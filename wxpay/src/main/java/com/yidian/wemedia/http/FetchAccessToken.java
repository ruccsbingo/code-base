package com.yidian.wemedia.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.bingo.exception.HttpInvokeException;

import java.util.HashMap;

/**
 * Created by zhangbing on 16/6/1.
 */
public class FetchAccessToken extends HttpInvoker{

    public static final TypeReference<HashMap> TYPEREF = new TypeReference<HashMap>() {
    };

    public FetchAccessToken(String baseUrl) {
        super(baseUrl);
    }

    public String fetch(){
        HashMap ret = httpGet(baseUrl, TYPEREF);
        isSuccess(ret);
        if(ret.containsKey("access_token")){
            return String.valueOf(ret.get("access_token"));
        }else {
            throw new HttpInvokeException("Error happens while invoking service, reason: access_token is empty");
        }
    }

    private boolean isSuccess(HashMap response) {
        if (response == null)
            throw new HttpInvokeException("Error happens while invoking service, reason: empty response");
        return true;
    }
}
