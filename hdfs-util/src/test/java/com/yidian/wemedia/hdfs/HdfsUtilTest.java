package com.yidian.wemedia.hdfs;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhangbing on 16/5/4.
 */
public class HdfsUtilTest {

    @Ignore
    @Test
    public void testGetInputStream() throws Exception {
        FSDataInputStream in = null;
        try {
            in = HdfsUtil.getInputStream();
            IOUtils.copyBytes(in, System.out, 50, false);
        }finally {
            IOUtils.closeStream(in);
        }
    }
}