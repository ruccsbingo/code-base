package com.bingo.mapper;

import com.bingo.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangbing on 16/11/27.
 */
@Mapper
public interface AccountMapper {
    Account getMediaRecordByDocId(@Param("docId") String docId);

    Long insertAccount(Account account);
}
