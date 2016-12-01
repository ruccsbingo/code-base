package com.bingo.configuration;

import com.bingo.enums.DataSourceType;

/**
 * Created by zhangbing on 16/11/26.
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /** * 读可能是多个库 */
    public static void read(int shardingIndex) {
        local.set(DataSourceType.read.getType() + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    /** * 写只有一个库 */
    public static void write(int shardingIndex) {
        local.set(DataSourceType.write.getType() + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    public static String getJdbcType() {
        return local.get();
    }
}
