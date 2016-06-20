package com.yidian.wemedia.http;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/6/1.
 */
public class FetchAccessTokenTest {

    @Test
    public void testFetch() throws Exception {
        FetchAccessToken fetchAccessToken = new FetchAccessToken("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                "wx2c55358f2acf43f6" +
                "&secret=3658d47ddd4fc9a12350c1f9a99e99d1");

        System.out.println(fetchAccessToken.fetch());
    }
}