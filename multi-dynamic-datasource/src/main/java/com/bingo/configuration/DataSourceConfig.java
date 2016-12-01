package com.bingo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by zhangbing on 16/11/26.
 */
@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${datasource.shardingSize}")
    public Integer shardingSize;

    @Value("${datasource.readSize}")
    public Integer dataSourceSize;

    @Value("${datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = writeBeanName1)
    @Primary
    @ConfigurationProperties(prefix = writePropPrefix1)
    public DataSource writeDataSourceOne() {
        log.debug("-------------------- " + writePropPrefix1 + " init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /** * 有多少个从库就要配置多少个 * @return */
    @Bean(name = readBeanName1to1)
    @ConfigurationProperties(prefix = readPropPrefix1to1)
    public DataSource readDataSourceOne2One() {
        log.debug("-------------------- " + readPropPrefix1to1 + " init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = readBeanName1to2)
    @ConfigurationProperties(prefix = readPropPrefix1to2)
    public DataSource readDataSourceOne2Two() {
        log.debug("-------------------- " + readPropPrefix1to2 + " init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = writeBeanName2)
    @ConfigurationProperties(prefix = writePropPrefix2)
    public DataSource writeDataSourceTwo() {
        log.debug("-------------------- " + writePropPrefix2 + " init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /** * 有多少个从库就要配置多少个 * @return */
    @Bean(name = readBeanName2to1)
    @ConfigurationProperties(prefix = readPropPrefix2to1)
    public DataSource readDataSourceTwo2One() {
        log.debug("-------------------- " + readPropPrefix2to1 + " init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = readBeanName2to2)
    @ConfigurationProperties(prefix = readPropPrefix2to2)
    public DataSource readDataSourceTwo2Two() {
        log.debug("-------------------- " + readPropPrefix2to2 + " init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    public static final char SHARDING_DELIMITER = '-';
    public static final char READ_DELIMITER = '-';

    public static final String writeBeanName1 = "writeDataSource" + SHARDING_DELIMITER + "1";
    public static final String readBeanName1to1 = "readDataSource" + SHARDING_DELIMITER + "1" + READ_DELIMITER + "1";
    public static final String readBeanName1to2 = "readDataSource" + SHARDING_DELIMITER + "1" + READ_DELIMITER + "2";

    public static final String writeBeanName2 = "writeDataSource" + SHARDING_DELIMITER + "2";
    public static final String readBeanName2to1 = "readDataSource" + SHARDING_DELIMITER + "2" + READ_DELIMITER + "1";
    public static final String readBeanName2to2 = "readDataSource" + SHARDING_DELIMITER + "2" + READ_DELIMITER + "2";

    public static final String writePropPrefix1 = "datasource.write" + SHARDING_DELIMITER + "1";
    public static final String readPropPrefix1to1 = "datasource.read" + SHARDING_DELIMITER + "1" + READ_DELIMITER + "1";
    public static final String readPropPrefix1to2 = "datasource.read" + SHARDING_DELIMITER + "1" + READ_DELIMITER + "2";

    public static final String writePropPrefix2 = "datasource.write" + SHARDING_DELIMITER + "2";
    public static final String readPropPrefix2to1 = "datasource.read" + SHARDING_DELIMITER + "2" + READ_DELIMITER + "1";
    public static final String readPropPrefix2to2 = "datasource.read" + SHARDING_DELIMITER + "2" + READ_DELIMITER + "2";
}
