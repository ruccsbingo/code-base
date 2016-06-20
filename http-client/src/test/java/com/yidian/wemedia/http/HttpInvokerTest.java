package com.yidian.wemedia.http;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/6/10.
 */
public class HttpInvokerTest {

    @Test
    public void testHttpGet() throws Exception {

    }

    @Test
    public void testHttpPost() throws Exception {
        MyInvoker httpInvoker = new MyInvoker("");
        httpInvoker.httpGet("http://10.111.0.65:6001/id/gen?url=http://www.yidianzixun.com/mp/content?id=5813759&token=d01bbc072c2e7376801d9aa0eb89f95a&type=news");
    }

    @Test
    public void testHttpPost1() throws Exception {

    }

    @Test
    public void testHttpDelete() throws Exception {

    }

    @Test
    public void testHttpGet1() throws Exception {

    }

    @Test
    public void testHttpGetStringResult() throws Exception {

    }

    @Test
    public void testHttpPostStringResult() throws Exception {

    }

    @Test
    public void testHttpPostRawResponse() throws Exception {

    }

    @Test
    public void testHttpGetRawResponse() throws Exception {

    }

    @Test
    public void testUrlEncoding() throws Exception {

    }

    @Test
    public void testDestroy() throws Exception {

    }

    @Test
    public void testGetBaseUrl() throws Exception {

    }

    @Test
    public void testSetBaseUrl() throws Exception {

    }
}