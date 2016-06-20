package com.bingo.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.bingo.exception.ErrorCode;
import com.bingo.exception.WeMediaException;

import java.util.Map;

/**
 * Created by Grey on 15/1/12.
 */
public class JSONService {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();

        //允许空对象
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //允许映射不上的列存在
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    }

    private JSONService() {
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static Map<String, Object> getMapFromJSONString(String JSON) {
        Map res;
        try {
            res = JSONService.getInstance().readValue(JSON, Map.class);
        } catch (Exception e) {
            throw new WeMediaException(ErrorCode.GeneralError, "JSON转换错误");
        }
        if (res == null) {
            throw new WeMediaException(ErrorCode.GeneralError, "JSON转换错误");
        }
        return res;
    }
}
