package com.yidian.wemedia;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * Created by zhangbing on 16/5/24.
 */
public class ConfigUtil {
    public static Config instance;
    static {
        instance = ConfigFactory.parseFile(new File("conf/config.properties"));
    }
    public static Config getInstance(){
        return instance;
    }
    private ConfigUtil(){
    }
}
