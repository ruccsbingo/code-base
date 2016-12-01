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
 */
@Aspect
@Component
public class DataSourceAop {

    private static final Logger log = LoggerFactory.getLogger(DataSourceAop.class);

    @Autowired
    DataSourceConfig dataSourceConfig;

    @Before("execution(* com.bingo.dao.AccountDao.find*(..)) or execution(* com.bingo.dao.AccountDao.get*(..))")
    public void setReadDataSourceType(JoinPoint point) {
        Object[] args = point.getArgs();
        int shardingIndex = detemineDatasourceSharding(args);
        DataSourceContextHolder.read(shardingIndex);
        log.info("dataSource切换到：Read" + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    @Before("execution(* com.bingo.dao.AccountDao.insert*(..)) or execution(* com.bingo.dao.AccountDao.update*(..))")
    public void setWriteDataSourceType(JoinPoint point) {
        Object[] args = point.getArgs();
        int shardingIndex = detemineDatasourceSharding(args);
        DataSourceContextHolder.write(shardingIndex);
        log.info("dataSource切换到：write" + DataSourceConfig.SHARDING_DELIMITER + shardingIndex);
    }

    private Long fetchUid(Object[] args) {
        if (args[0].getClass() == Long.class) {
            return (Long) args[0];
        } else {
            throw new IllegalArgumentException("first parameter should be uid");
        }
    }

    //根据用户的uid % sharding 获取数据存储的分区
    private int detemineDatasourceSharding(Object[] args) {
        Long uid = fetchUid(args);
        return (int) ((uid % dataSourceConfig.shardingSize) + 1);
    }
}
