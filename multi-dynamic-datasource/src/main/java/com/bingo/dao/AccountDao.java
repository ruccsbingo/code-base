package com.bingo.dao;

import com.bingo.mapper.AccountMapper;
import com.bingo.model.Account;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangbing on 16/11/27.
 */
@Component
public class AccountDao {
    @Autowired
    private SqlSession sqlSession;

    public Account getByPK(Long id) {
        return this.sqlSession.getMapper(AccountMapper.class).getByPK(id);
    }

    public Long insert(Account account) {
        return this.sqlSession.getMapper(AccountMapper.class).insert(account);
    }
}
