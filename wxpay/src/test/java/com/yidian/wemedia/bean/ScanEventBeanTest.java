package com.yidian.wemedia.bean;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhangbing on 16/5/27.
 */
public class ScanEventBeanTest {

    @Ignore
    @Test
    public void testToString() throws Exception {
        ScanEventBean scanEventBean = new ScanEventBean("toUser",
                "FromUser",
                "12345678",
                "event",
                "subscribe",
                "qrscene_123123",
                "TICKET");

        System.out.println(scanEventBean.toString());
    }
}