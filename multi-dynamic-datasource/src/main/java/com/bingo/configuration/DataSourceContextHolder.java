package com.bingo.configuration;

import com.bingo.enums.DataSourceType;

/**
 * Created by zhangbing on 16/11/26.
 * 线程安全的数据源Context
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 设置读库和分区标志
     * @param shardingIndex: 分库标志
     */
    public static void read(int shardingIndex) {
        local.set(DataSourceType.read.getType() + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    /**
     * 设置写库和分区标志
     * @param shardingIndex
     */
    public static void write(int shardingIndex) {
        local.set(DataSourceType.write.getType() + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    /**
     * 获取当前的数据源
     * @return
     */
    public static String getJdbcType() {
        return local.get();
    }
}
