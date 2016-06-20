package com.bingo.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Grey on 15/1/9.
 * <p/>
 * 基础数据库查询接口，所有表操作都要有的功能
 * 实现 计数 主键获取 获得全部 全域添加 全域修改 绝对删除
 */
public interface BasicDataManager<T> {
    int countAll();

    T getByPK(@Param("id") long id);

    List<T> getAll();

    //全域添加，域id 为添加后的主键
    long insert(T t);

    //全域修改
    int updateAll(final T t);

    //map域修改
    int updateFields(@Param("id") long id, @Param("map") Map<String, Object> map);

    //绝对删除
    int realDelete(final long id);
}
