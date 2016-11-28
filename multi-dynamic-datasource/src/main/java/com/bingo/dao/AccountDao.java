package com.bingo.dao;

import com.bingo.mapper.AccountMapper;
import com.bingo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangbing on 16/11/27.
 */
@Component
public class AccountDao {
    @Autowired
    AccountMapper accountMapper;

    Account getMediaRecordByDocId(String docId) {
        return accountMapper.getMediaRecordByDocId(docId);
    }
}
