package com.bingo.configuration;

import com.bingo.enums.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangbing on 16/11/26.
 * 动态选择数据源
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {
    private final int dataSourceNumber;
    private AtomicInteger count = new AtomicInteger(0);

    public MyAbstractRoutingDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    /**
     * 从数据源Context中,获取当前设置的数据源类型,和分区标志,
     * 如果是读类型操作,进行简单的负载均衡
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getJdbcType();
        int shardingIndex = Integer.parseInt(
                typeKey.substring(
                        typeKey.indexOf(DataSourceConfig.SHARDING_DELIMITER) + 1,
                        typeKey.length()));

        if (typeKey.contains(DataSourceType.write.getType())) {
            return DataSourceType.write.getType() + DataSourceConfig.SHARDING_DELIMITER + shardingIndex;
        } else {
            // 读 简单负载均衡
            int number = count.getAndAdd(1);
            if (number > 1000000000) {
                count.set(0);
            }
            int lookupKey = number % dataSourceNumber + 1;
            return DataSourceType.read.getType() + DataSourceConfig.SHARDING_DELIMITER + shardingIndex + DataSourceConfig.READ_DELIMITER + lookupKey;
        }
    }
}
