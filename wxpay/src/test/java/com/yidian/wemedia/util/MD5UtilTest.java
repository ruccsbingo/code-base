package com.yidian.wemedia.util;

import com.yidian.wemedia.bean.MyField;
import com.yidian.wemedia.bean.PayRequestBean;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/5/27.
 */
public class MD5UtilTest {

    @Ignore
    @Test
    public void testMD5() throws Exception {
        MD5Util.MD5("");
    }

    @Ignore
    @Test
    public void testMD5Sign() throws Exception {
        List<MyField> list = BeanUtil.getMyFieldList(PayRequestBean.class);
        PayRequestBean payRequestBean = new PayRequestBean("1000",
                "NO_CHECK",
                "test",
                "wx2c55358f2acf43f6",
                "1344075701",
                "3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS",
                "o5UqIxAVUcsZLBls3sMLDVYZKcBE",
                "100000982014120919625",
                "张兵",
                "127.0.0.1",
                "1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik9");
        String sign = MD5Util.MD5Sign("1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik9", list, payRequestBean);
        assertEquals("EE78791B1295F67BCC813234CF6DFDBC", sign);
    }
}