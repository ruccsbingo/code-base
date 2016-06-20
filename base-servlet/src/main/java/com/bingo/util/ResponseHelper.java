package com.bingo.util;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author weijian
 * Date : 2014-09-26 10:57
 */

public final class ResponseHelper {

    private ResponseHelper() {
        throw new AssertionError();
    }

    public static void setHeader(final ServletResponse resp,
                                 final String contentType,
                                 final String charset){
        checkNotNull(resp, "resp");
        checkNotNull(contentType, "contentType");
        checkNotNull(charset, "charset");
        resp.setContentType(contentType);
        resp.setCharacterEncoding(charset);
    }


    public static void setJsonResponse(final ServletResponse resp){
        setHeader(resp, "application/json", "UTF-8");
    }

    public static void setHtmlResponse(final ServletResponse resp,
                                       final String charset){
        setHeader(resp, "text/html", charset);
    }

    public static void setHtmlResponse(final ServletResponse resp){
        setHtmlResponse(resp, "UTF-8");
    }

    public static void sendBody(final ServletResponse resp,
                                final String body)
            throws IOException {
        checkNotNull(resp, "resp");
        checkNotNull(body, "body");
        PrintWriter writer = resp.getWriter();
        writer.write(body);
        writer.close();
    }
}
