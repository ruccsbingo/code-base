package com.yidian.wemedia.convertor;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhangbing on 16/5/27.
 */
public class ScanEventConvertorTest {

    @Ignore
    @Test
    public void testConvert() throws Exception {
//
        String data = "<?xml version='1.0' encoding='UTF-8'?>" +
                "<xml>" +
                "<CreateTime>12345678</CreateTime>" +
                "<Event>subscribe</Event>" +
                "<EventKey>qrscene_123123</EventKey>" +
                "<FromUserName>FromUser</FromUserName>" +
                "<MsgType>event</MsgType>" +
                "<Ticket>TICKET</Ticket>" +
                "<ToUserName>toUser</ToUserName>" +
                "</xml>";

        System.out.println(ScanEventConvertor.convert(data));
    }
}