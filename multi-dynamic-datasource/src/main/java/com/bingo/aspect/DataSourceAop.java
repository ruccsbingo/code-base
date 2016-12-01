package com.bingo.aspect;

import com.bingo.configuration.DataSourceConfig;
import com.bingo.configuration.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangbing on 16/11/26.
 * 依据函数名决定使用读库还是写库;
 * 依据第一个参数决定sharding库.
 */
@Aspect
@Component
public class DataSourceAop {

    private static final Logger log = LoggerFactory.getLogger(DataSourceAop.class);

    @Autowired
    DataSourceConfig dataSourceConfig;

    /**
     * 设定为读库,并依据第一个参数选择sharding库
     * @param point
     */
    @Before("execution(* com.bingo.dao.*.find*(..))" +
            " or execution(* com.bingo.dao.*.get*(..))" +
            " or execution(* com.bingo.dao.*.select*(..))")
    public void setReadDataSourceType(JoinPoint point) {
        Object[] args = point.getArgs();
        int shardingIndex = detemineDatasourceSharding(args);
        DataSourceContextHolder.read(shardingIndex);
        log.debug("dataSource切换到：Read" + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    /**
     * 设定为写库,并依据第一个参数选择sharding库
     * @param point
     */
    @Before("execution(* com.bingo.dao.*.insert*(..))" +
            " or execution(* com.bingo.dao.*.update*(..))" +
            " or execution(* com.bingo.dao.*.delete*(..))")
    public void setWriteDataSourceType(JoinPoint point) {
        Object[] args = point.getArgs();
        int shardingIndex = detemineDatasourceSharding(args);
        DataSourceContextHolder.write(shardingIndex);
        log.debug("dataSource切换到：write" + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    private Long fetchUid(Object[] args) {
        if (args[0].getClass() == Long.class) {
            return (Long) args[0];
        } else {
            throw new IllegalArgumentException("first parameter should be uid");
        }
    }

    /**
     * 根据用户的uid % sharding 获取数据存储的分区
     */
    protected int detemineDatasourceSharding(Object[] args) {
        Long uid = fetchUid(args);
        return (int) ((uid % dataSourceConfig.shardingSize) + 1);
    }
}
