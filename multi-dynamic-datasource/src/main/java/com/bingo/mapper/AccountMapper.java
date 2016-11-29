package com.bingo.mapper;

import com.bingo.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangbing on 16/11/27.
 */
@Mapper
@Repository
public interface AccountMapper {
    Account getByPK(@Param("id") Long id);

    Long insert(Account account);
}
