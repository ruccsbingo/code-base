package com.yidian.wemedia.bean;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhangbing on 16/5/26.
 */
public class PayRequestBeanTest {

    @Ignore
    @Test
    public void testToString() throws Exception {
        PayRequestBean payRequestBean = new PayRequestBean("1000",
                "NO_CHECK",
                "test",
                "wx2c55358f2acf43f6",
                "1344075701",
                "3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS",
                "o5UqIxAVUcsZLBls3sMLDVYZKcBE",
                "100000982014120919623",
                "张兵",
                "127.0.0.1",
                "419523EBE31ED5B3ECF11CED54B30BDC");

        System.out.println(payRequestBean.toString());

    }
}