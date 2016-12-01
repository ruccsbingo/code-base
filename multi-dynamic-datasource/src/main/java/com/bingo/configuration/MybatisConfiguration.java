package com.bingo.configuration;

import com.bingo.enums.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangbing on 16/11/26.
 */
@Configuration
@AutoConfigureAfter({ DataSourceConfig.class })
public class MybatisConfiguration extends MybatisAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Autowired
    DataSourceConfig dataSourceConfig;

    @Bean
    public SqlSessionFactory sqlSessionFactorys() throws Exception {
        log.info("-------------------- 重载父类 sqlSessionFactory init ---------------------");
        return super.sqlSessionFactory(roundRobinDataSouceProxy());
    }

    /** * 有多少个数据源就要配置多少个bean * @return */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        int size = dataSourceConfig.dataSourceSize;
        int sharding = dataSourceConfig.shardingSize;

        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        //设置默认的数据源
//        DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");
//        proxy.setDefaultTargetDataSource(writeDataSource);

        for (int i=0; i<sharding; i++) {
            targetDataSources.put(DataSourceType.write.getType() + DataSourceConfig.SHARDING_DELIMITER + (i + 1),
                    SpringContextHolder.getBean("writeDataSource" + DataSourceConfig.SHARDING_DELIMITER + (i + 1)));
            for (int j = 0; j < size; j++) {
                targetDataSources.put(
                        DataSourceType.read.getType() + DataSourceConfig.SHARDING_DELIMITER + (i + 1) + DataSourceConfig.READ_DELIMITER + (j + 1),
                        SpringContextHolder.getBean("readDataSource" + DataSourceConfig.SHARDING_DELIMITER + (i + 1) + DataSourceConfig.READ_DELIMITER + (j + 1)));
            }
        }

        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}
