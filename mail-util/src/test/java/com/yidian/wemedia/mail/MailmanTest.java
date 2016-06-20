package com.yidian.wemedia.mail;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhangbing on 16/3/24.
 */
public class MailmanTest {

    @Ignore
    @Test
    public void testSendMail() throws Exception {
        Mailman mailman = new Mailman("pingpangbing0902@126.com");
        mailman.sendMail(new WarningMail("test"));
        Thread.sleep(10000);
    }

    @Test
    public void testRetire() throws Exception {

    }
}