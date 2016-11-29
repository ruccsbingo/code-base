package com.bingo.configuration;

import com.bingo.enums.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangbing on 16/11/26.
 */
@Configuration
@AutoConfigureAfter({ DataSourceConfiguration.class })
public class MybatisConfiguration extends MybatisAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Value("${datasource.readSize}")
    private String dataSourceSize;

    @Bean
    public SqlSessionFactory sqlSessionFactorys() throws Exception {
        log.info("-------------------- 重载父类 sqlSessionFactory init ---------------------");
        return super.sqlSessionFactory(roundRobinDataSouceProxy());
    }

    /** * 有多少个数据源就要配置多少个bean * @return */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        int size = Integer.parseInt(dataSourceSize);
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");
        // 写
        targetDataSources.put(DataSourceType.write.getType(), SpringContextHolder.getBean("writeDataSource"));

        for (int i = 0; i < size; i++) {
            targetDataSources.put(i, SpringContextHolder.getBean("readDataSource" + (i + 1)));
        }
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}
