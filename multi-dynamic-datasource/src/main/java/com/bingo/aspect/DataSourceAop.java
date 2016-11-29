package com.bingo.aspect;

import com.bingo.configuration.DataSourceContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhangbing on 16/11/26.
 */
@Aspect
@Component
public class DataSourceAop {

    private static final Logger log = LoggerFactory.getLogger(DataSourceAop.class);

    @Before("execution(* com.bingo.dao.AccountDao.find*(..)) or execution(* com.bingo.dao.AccountDao.get*(..))")
    public void setReadDataSourceType() {
        DataSourceContextHolder.read();
        log.info("dataSource切换到：Read");
    }

    @Before("execution(* com.bingo.dao.AccountDao.insert*(..)) or execution(* com.bingo.dao.AccountDao.update*(..))")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.write();
        log.info("dataSource切换到：write");
    }
}
