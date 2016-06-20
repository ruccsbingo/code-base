package com.bingo.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.io.IOException;
import java.io.InputStream;

/**
 * Util Class for Mybatis
 * 支持Mybatis实例化和相关工作的类
 * Created by Grey on 15/1/7.
 */
public class MyBatisUtil {

    public static SqlSessionFactory sessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream iS = Resources.getResourceAsStream(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(iS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SqlSession getSqlSession() {
        return getSessionFactory().openSession(true);
    }

    public static SqlSession getTransactinSession(){
        return getSessionFactory().openSession(TransactionIsolationLevel.REPEATABLE_READ);
    }
}
