package com.yidian.wemedia.convertor;

import com.google.common.base.Preconditions;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by zhangbing on 16/5/9.
 */
public class LogUtil {

    private static final Logger LOG = LoggerFactory.getLogger(LogUtil.class);

    private LogUtil() {
        throw new AssertionError();
    }

    public static void configureLog4jProp(String resource) {
        Preconditions.checkNotNull(resource, "resource");
        URL url = LogUtil.class.getResource("/" + resource);
        if(url == null) {
            url = LogUtil.class.getResource("/conf/" + resource);
            PropertyConfigurator.configure(url);
            LOG.warn("Load default " + resource);
        } else {
            PropertyConfigurator.configure(url);
        }
    }
}
