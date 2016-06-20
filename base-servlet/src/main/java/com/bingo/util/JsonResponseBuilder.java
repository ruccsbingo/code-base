package com.bingo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author weijian
 * Date : 2014-09-26 11:18
 */

public final class JsonResponseBuilder {

    public static final String SUCCESS_STATUS = "success";
    public static final String FAILED_STATUS = "failed";
    public static final int FAILED_CODE = 0;

    private final ServletResponse response;

    private final Map<String, Object> data;

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonResponseBuilder(ServletResponse response) {
        this.response = checkNotNull(response, "response");
        ResponseHelper.setJsonResponse(response);
        this.data = new HashMap<String, Object>(10);

    }

    public static JsonResponseBuilder builder(ServletResponse response){
        return new JsonResponseBuilder(response);
    }

    public ServletResponse build() throws IOException {
        String jsonBody;
        if (data.isEmpty()){
            jsonBody = "{\"status\": \"" + SUCCESS_STATUS + "\"}";
        } else {
            jsonBody = OBJECT_MAPPER.writeValueAsString(data);
        }
        ResponseHelper.sendBody(response, jsonBody);
        return response;
    }

    public JsonResponseBuilder setFailed(String reason) {
        checkNotNull(reason, "reason");
        this.data.put("status", FAILED_STATUS);
        this.data.put("reason", reason);
        return this;
    }

    public JsonResponseBuilder setSuccess() {
        this.data.put("status", SUCCESS_STATUS);
        return this;
    }

    public JsonResponseBuilder setCode(int code){
        this.data.put("code", code);
        return this;
    }

    public JsonResponseBuilder setSuccessWithData(Object obj){
        checkNotNull(obj, "obj");
        this.data.put("status", SUCCESS_STATUS);
        this.data.put("result", obj);
        return this;
    }

    public JsonResponseBuilder setSuccessWithData(Map<String, ?> map){
        checkNotNull(map, "map");
        this.data.put("status", SUCCESS_STATUS);
        this.data.put("result", map);
        return this;
    }

    public JsonResponseBuilder setSuccessWithData(String key, Object value){
        checkNotNull(key, "key");
        checkNotNull(value, "value");
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put(key, value);
        return setSuccessWithData(map);
    }

    public JsonResponseBuilder setSuccessWithData(String k1, Object v1,
                                                  String k2, Object v2){
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put(checkNotNull(k1, "k1"), checkNotNull(v1, "v1"));
        map.put(checkNotNull(k2, "k2"), checkNotNull(v2, "v2"));
        return setSuccessWithData(map);
    }

    public JsonResponseBuilder setSuccessWithData(String k1, Object v1,
                                                  String k2, Object v2,
                                                  String k3, Object v3){
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put(checkNotNull(k1, "k1"), checkNotNull(v1, "v1"));
        map.put(checkNotNull(k2, "k2"), checkNotNull(v2, "v2"));
        map.put(checkNotNull(k3, "k3"), checkNotNull(v3, "v3"));
        return setSuccessWithData(map);
    }

    public Map getMapFromJson(String jsonStr) throws IOException {
        checkNotNull(jsonStr);
        return OBJECT_MAPPER.readValue(jsonStr, HashMap.class);
    }

    public <T> Map getMapFromJson(InputStream src, Class<T> valueType) throws IOException {
        checkNotNull(src);
        return OBJECT_MAPPER.readValue(src, HashMap.class);
    }

    public static String createJson(Object obj) throws JsonProcessingException {
        if(obj == null){
            return "[]";
        }
        return OBJECT_MAPPER.writeValueAsString(obj);
    }
}
