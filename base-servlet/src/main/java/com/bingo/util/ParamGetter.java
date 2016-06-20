package com.bingo.util;

import com.bingo.exception.InvalidFormatException;

import javax.servlet.ServletRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author weijian
 * Date : 2015-03-04 17:09
 */

public final class ParamGetter {

    private final ServletRequest req;

    public ParamGetter(ServletRequest req) {
        this.req = checkNotNull(req, "req");
    }


    public String getString(String key)
            throws InvalidFormatException.InvalidRequestParameterException {
        String val = req.getParameter(key);
        ParameterChecker.checkNotNullOrEmpty(val, key);
        return val;
    }

    public int getInt(String key)
            throws InvalidFormatException.InvalidRequestParameterException {
        String param = req.getParameter(key);
        ParameterChecker.checkNotNullOrEmpty(param, key);

        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException.InvalidRequestParameterException(
                    ParameterChecker.format("param {} is not integer: {}", key, e.getMessage())
            );
        }
    }

    public long getLong(String key)
            throws InvalidFormatException.InvalidRequestParameterException {
        String param = req.getParameter(key);
        ParameterChecker.checkNotNullOrEmpty(param, key);

        try {
            return Long.parseLong(param);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException.InvalidRequestParameterException(
                    ParameterChecker.format("param {} is not Long: {}", key, e.getMessage())
            );
        }
    }
}
